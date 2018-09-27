package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jinyan on 3/14/18 2:44 PM.
 */
public class SocketDemo {
    private static final Logger logger = LoggerFactory.getLogger(SocketDemo.class);

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8080);
        OutputStream outputStream = socket.getOutputStream();
        Boolean autoFlush = true;
        PrintWriter printWriter = new PrintWriter(outputStream,autoFlush);
        printWriter.println("GET /index.jsp HTTP/1.1");
        printWriter.println("Host:localhost:8080");
        printWriter.println("Connection:Close");
        printWriter.println();

        Boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (loop) {
            if (bufferedReader.ready()) {
                int i = 0;
                while (i != -1) {
                    i = bufferedReader.read();
                    sb.append((char) i);
                }
                loop = false;
            }
        }
        System.out.println(sb);
        socket.close();
    }


}


