package basicExercise.thrift;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Test Client
 *
 * @author hanhan.zhang
 */
public class QTestClient {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static ByteBuffer decode(String msg) {
        int length = msg.length();
        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.put(msg.getBytes());
        buffer.flip();
        return buffer;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 消息编码
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        Socket socket = socketChannel.socket();
        socket.setTcpNoDelay(true);
        // 设置TCP发送缓冲区大小,防止丢包
        socket.setSendBufferSize(102400);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 6721));

        while (true) {
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (!key.isValid()) {
                    continue;
                }

                SocketChannel sc = (SocketChannel) key.channel();

                if (key.isConnectable()) {
                    if (sc.isConnectionPending()) {
                        boolean connected = sc.finishConnect();
                        if (connected) {
                            System.out.println("client connect server success !");
                        }
                    }
                    String content = "client send heart beat at " + SDF.format(new Date());
                    sc.write(decode(content));
                } else if (key.isReadable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = sc.read(buffer);
                    if (len > 0) {
                        buffer.flip();
                        byte arr[] = new byte[buffer.remaining()];
                        buffer.get(arr);
                        System.out.println("receive : " + new String(arr, "UTF-8"));
                        buffer.clear();
                    }

                    String content = "client send heart beat at " + SDF.format(new Date());
                    // 写数据
                    sc.write(decode(content));
                }
            }
        }
    }

}
