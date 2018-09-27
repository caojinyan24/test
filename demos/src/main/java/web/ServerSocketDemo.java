package web;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jinyan on 3/14/18 3:42 PM.
 */
public class ServerSocketDemo {
    //浏览器访问:http://127.0.0.1:8080/Servlet
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            StringBuffer request = new StringBuffer(2048);
            byte[] buffer = new byte[2048];
            int i = inputStream.read(buffer);
            for (int j = 0; j < i; j++) {
                request.append((char) buffer[j]);
            }
            System.out.println(request);
            int index1, index2;
            index1 = request.indexOf(" ");
            String uri = "";
            if (index1 != -1) {
                index2 = request.indexOf(" ", index1 + 1);
                if (index2 > index1) {
                    uri = request.substring(index1 + 1, index2);
                }
            }
            if (uri.startsWith("/ServletImpl")) {
                new ServletProcessor().process(request.toString(), outputStream);
            }


            String msg = "HTTP/1.1 404 File not Fount\r\n";
            msg += "Content-Type:text/html\r\n";
            msg += "Content-Length:23\r\n";
            msg += "\r\n";
            msg += "<h1>File not Fount</h1>";
            outputStream.write(msg.getBytes());
            outputStream.close();
            serverSocket.close();
        }
    }
}
