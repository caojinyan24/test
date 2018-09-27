package basicExercise.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by jinyancao on 2/8/17.
 */
public class Main {
    public void nioOperator() {
        try {
            Selector selector = Selector.open();
            selector.select();
            Channel channel = selector.provider().openServerSocketChannel();
            /**
             * 注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
             Connect
             Accept
             Read
             Write
             通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。

             这四种事件用SelectionKey的四个常量来表示：

             SelectionKey.OP_CONNECT
             SelectionKey.OP_ACCEPT
             SelectionKey.OP_READ
             SelectionKey.OP_WRITE

             如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来，如下：
             1
             int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
             */
            while (true) {
                int readChannels = selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    try {
                        if (key.isAcceptable()) {

                        } else if (key.isConnectable()) {

                        } else if (key.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            if (socketChannel.isOpen()) {
                                socketChannel.read(buffer);
                                buffer.flip();
                                System.out.println(new String(buffer.array()));
                            }
                        } else if (key.isWritable()) {

                        }
                        keyIterator.remove();
                    } catch (Exception e) {
                        key.cancel();
                        key.channel().close();
                    }
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //    public void printData(Data){
//
//    }
    public void read(String fileName) {
        FileInputStream stream = null;
        FileChannel channel = null;
        try {
//            file = new File(ClassLoader.getSystemResource(fileName).getPath());
//            BufferedReader reader=new BufferedReader(new FileReader(file));
//            String s=null;
//            while((s=reader.readLine())!=null){
//
//            }
            stream = new FileInputStream(new File(this.getClass().getResource(fileName).getPath()));
            channel = stream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            byte[] bytes = buffer.array();
            System.out.println(new String(bytes));

            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.read("/phone");

    }
}
