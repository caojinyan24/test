package basicExercise.java.mbean;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * Created by jinyan on 4/1/18 11:08 PM.
 */
public class ClientTest {
    public static void main(String[] args) {
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            ObjectName mbeanName = new ObjectName("jmxdemo:type=User");

            //设置指定Mbean的特定属性值  //这里的setAttribute、getAttribute操作只能针对bean的属性
            //例如对getName或者setName进行操作，只能使用Name，需要去除方法的前缀
            mbsc.setAttribute(mbeanName, new Attribute("Password", "pass"));
            mbsc.setAttribute(mbeanName, new Attribute("Username", "user"));
            String password = (String) mbsc.getAttribute(mbeanName, "Password");
            String username = (String) mbsc.getAttribute(mbeanName, "Username");
            System.out.println("password=" + password + ";username=" + username);

            UserMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, UserMBean.class, false);
            proxy.test();//在server端(MBeanTest)调用test方法
            mbsc.invoke(mbeanName, "test", null, null);//在server端(MBeanTest)调用test方法
            //invoke调用bean的方法，只针对非设置属性的方法,不能对get和set方法做调用
            //mbsc.invoke(mbeanName, "getUsername", null, null);//会报错
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
