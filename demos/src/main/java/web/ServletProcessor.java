package web;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponseWrapper;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(String input, OutputStream outputStream) throws Exception {


        int index1, index2;
        index1 = input.indexOf(" ");
        String uri = "";
        if (index1 != -1) {
            index2 = input.indexOf(" ", index1 + 1);
            if (index2 > index1) {
                uri = input.substring(index1 + 1, index2);
            }
        }
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(System.getProperty("user.dir") + File.separator + "demos/target/classes/web");
            // the forming of repository is taken from the createClassLoader method in
            // org.apache.catalina.startup.ClassLoaderFactory
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            // the code for forming the URL is taken from the addRepository method in
            // org.apache.catalina.loader.StandardClassLoader class.
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        try {
            ServletImpl servlet = (ServletImpl) myClass.newInstance();
            servlet.service(new ServletRequestWrapper(null), new ServletResponseWrapper(null));
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}