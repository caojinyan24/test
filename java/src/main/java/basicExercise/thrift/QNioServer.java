package basicExercise.thrift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test Nio Server
 *
 * @author hanhan.zhang
 */
public class QNioServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QNioServer.class);

    private AtomicBoolean _stopped = new AtomicBoolean(true);

    private QAcceptThread acceptThread;

    private Set<QSelectorThread> selectorThreads = new HashSet<QSelectorThread>();

    // 业务线程池
    private ExecutorService workExecutor;


    public static class QServerArgs {
        // 绑定地址
        private String host;
        // 绑定端口
        private int port;

        private int selectorThreadNumber;

        private int queueSizePerSelectorThread;

        private int minWorkerThread;

        private int maxWorkerThread;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getSelectorThreadNumber() {
            return selectorThreadNumber;
        }

        public void setSelectorThreadNumber(int selectorThreadNumber) {
            this.selectorThreadNumber = selectorThreadNumber;
        }

        public int getQueueSizePerSelectorThread() {
            return queueSizePerSelectorThread;
        }

        public void setQueueSizePerSelectorThread(int queueSizePerSelectorThread) {
            this.queueSizePerSelectorThread = queueSizePerSelectorThread;
        }

        public int getMinWorkerThread() {
            return minWorkerThread;
        }

        public void setMinWorkerThread(int minWorkerThread) {
            this.minWorkerThread = minWorkerThread;
        }

        public int getMaxWorkerThread() {
            return maxWorkerThread;
        }

        public void setMaxWorkerThread(int maxWorkerThread) {
            this.maxWorkerThread = maxWorkerThread;
        }
    }

    /**
     * 连接请求处理线程
     */
    private class QAcceptThread extends Thread {
        // 绑定地址
        private String host;
        // 绑定端口
        private int port;

        private Selector acceptSelector;

        private QSelectorThreadLoadBalancer selectorThreadChoose;

        public QAcceptThread(QSelectorThreadLoadBalancer loadBalancer, String host, int port) throws IOException {
            this.host = host;
            this.port = port;
            selectorThreadChoose = loadBalancer;
            acceptSelector = Selector.open();

            // ServerSocketChannel需设置非阻塞模式且只监听SelectionKey.OP_ACCEPT事件
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(this.host, this.port));
            ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {
            try {
                while (!_stopped.get()) {
                    acceptSelector.select();

                    Iterator<SelectionKey> it = acceptSelector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            Socket socket = sc.socket();
                            socket.setKeepAlive(true);
                            socket.setTcpNoDelay(true);

                            // 接受客户端连接并将客户端连接通信交由给QSelectorThread处理
                            doAddAccept(sc, selectorThreadChoose.nextSelectorThread());
                        }
                    }
                }
            } catch (Throwable e) {
                LOGGER.error("QAcceptThread exit dut to uncaught error", e);
            }
        }

        private void doAddAccept(SocketChannel sc, QSelectorThread selectorThread) throws IOException {
            selectorThread.doAddAccept(sc);
        }
    }

    /**
     * QSelectorThreadLoadBalancer负责QSelectorThread线程均匀的处理客户端连接
     */
    private class QSelectorThreadLoadBalancer {

        private Set<QSelectorThread> selectorThreads;

        private Iterator<QSelectorThread> iterator;

        public QSelectorThreadLoadBalancer(Set<QSelectorThread> selectorThreads) {
            this.selectorThreads = selectorThreads;
            iterator = selectorThreads.iterator();
        }

        public QSelectorThread nextSelectorThread() {
            if (!iterator.hasNext()) {
                iterator = selectorThreads.iterator();
            }
            return iterator.next();
        }
    }

    /**
     * 负责客户端连接
     */
    private class QSelectorThread extends Thread {

        private Selector selector;

        private BlockingQueue<SocketChannel> acceptQueue;

        public QSelectorThread(int size) throws IOException {
            selector = Selector.open();
            if (size <= 0) {
                acceptQueue = new LinkedBlockingQueue<SocketChannel>();
            } else {
                acceptQueue = new ArrayBlockingQueue<SocketChannel>(size);
            }
        }

        @Override
        public void run() {
            try {
                while (!_stopped.get()) {
                    select();
                    processAcceptConnection();
                }
            } catch (Throwable e) {
                LOGGER.error("QSelectorThread exit dut to uncaught error", e);
            }

        }

        private void select() {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (!_stopped.get() && iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isReadable()) {
                        handleSocketRead(key);
                    } else if (key.isWritable()) {
                        handleSocketWrite(key);
                    } else {
                        LOGGER.warn("Unexpected state in select! " + key.interestOps());
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Got an IOException while selector thread selecting!", e);
            }
        }

        private void processAcceptConnection() throws IOException {
            while (!_stopped.get()) {
                SocketChannel sc = acceptQueue.poll();
                if (sc == null) {
                    break;
                }
                sc.configureBlocking(false);
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                /**
                 * 每个客户端对应一个{@link QFrameBuffer}
                 * */
                QFrameBuffer buffer = new QFrameBuffer(sc, sk);
                sk.attach(buffer);
            }
        }

        public void doAddAccept(SocketChannel sc) throws IOException {
            if (!_stopped.get()) {
                acceptQueue.add(sc);
                // 唤醒Selector[由于Selector.select()阻塞]
                selector.wakeup();
            } else {
                sc.close();
            }
        }

        private void handleSocketRead(SelectionKey key) {
            final QFrameBuffer buffer = (QFrameBuffer) key.attachment();
            if (buffer != null) {
                // Socket读取异常处理:
                // 1: 关闭Socket
                // 2: SelectionKey.cancel()
                if (!buffer.doRead()) {
                    buffer.cleanupSelectionKey(key);
                    return;
                }

                // Socket数据包读取完成后,交由业务线程处理
                // Note:
                //     业务线程处理异常,关闭Socket
                if (buffer.isFrameFullyRead()) {
                    if (workExecutor != null) {
                        try {
                            workExecutor.submit(new Runnable() {
                                public void run() {
                                    buffer.invoke();
                                }
                            });
                        } catch (RejectedExecutionException e) {
                            buffer.cleanupSelectionKey(key);
                        }
                    } else {
                        buffer.invoke();
                    }
                }
            }
        }

        private void handleSocketWrite(SelectionKey key) {
            QFrameBuffer buffer = (QFrameBuffer) key.attachment();
            if (buffer != null) {
                // Socket写数据失败, 关闭Socket
                if (!buffer.doWrite()) {
                    buffer.cleanupSelectionKey(key);
                }
            }
        }
    }

    public QNioServer(QServerArgs args) throws IOException {
        for (int i = 0; i < args.getSelectorThreadNumber(); ++i) {
            selectorThreads.add(new QSelectorThread(args.getQueueSizePerSelectorThread()));
        }
        acceptThread = new QAcceptThread(new QSelectorThreadLoadBalancer(selectorThreads), args.getHost(), args.getPort());
        workExecutor = new ThreadPoolExecutor(args.getMinWorkerThread(), args.getMaxWorkerThread(), 5, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
    }

    private void startThread() {
        _stopped.set(false);
        for (QSelectorThread thread : selectorThreads) {
            thread.start();
        }
        acceptThread.start();
    }

    private void waitForShutdown() {
        try {
            joinThreads();
            shutdownGracefully();
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while joining threads!", e);
        }
    }

    private void joinThreads() throws InterruptedException {
        acceptThread.join();
        for (QSelectorThread selectorThread : selectorThreads) {
            selectorThread.join();
        }
    }

    private void shutdownGracefully() throws InterruptedException {
        workExecutor.shutdown();
        while (workExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            workExecutor.shutdownNow();
        }
    }

    public void serve() {
        startThread();
        waitForShutdown();
    }

    public static void main(String[] args) {
        System.out.println(1 << 0);
    }
}
