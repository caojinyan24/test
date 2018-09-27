package basicExercise.java.mbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * Created by jinyan on 3/28/18 5:16 PM.
 */
public class MBeanTest {
    public static void main(String[] args) {
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("jmxdemo:type=User");
            User user = new User();
            beanServer.registerMBean(user, objectName);

            //这个步骤很重要，注册一个端口，绑定url后用于客户端通过rmi方式连接JMXConnectorServer
            LocateRegistry.createRegistry(9999);
            //URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, beanServer);
            jcs.start();

            String oldusername = null;
            String oldpassword = null;

            while (true) {
                if (oldusername != user.getUsername() || oldpassword != user.getPassword()) {
                    System.out.println(user.getUsername() + ',' + user.getPassword());
                    oldusername = user.getUsername();
                    oldpassword = user.getPassword();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


