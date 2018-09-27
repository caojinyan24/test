//package basicExercise.web;
//
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Created by jinyan on 10/11/17 4:01 PM.
// */
//public class JettyDemo extends AbstractHandler {
//    private static final Logger logger = LoggerFactory.getLogger(JettyDemo.class);
//
//    @Override
//    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
//        httpServletResponse.setContentType("text/html;charset=utf-8");
//        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//        httpServletResponse.getWriter().println("<h1>hello world</h1>");
//        request.setHandled(true);
//    }
//
//    public static void main(String[] args) throws Exception {
//        Server server = new Server(8080);
//        server.setHandler(new JettyDemo());
//        server.start();
//        server.join();
//    }
//}
