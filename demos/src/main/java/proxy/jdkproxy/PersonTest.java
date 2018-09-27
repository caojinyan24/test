package proxy.jdkproxy;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Created by jinyancao on 4/18/16.
 */
@RunWith(JUnit4.class)
public class PersonTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void Test() {
        JDKProxyFactory factory = new JDKProxyFactory();
        //通过代理类生成一个实例对象（织入后）
        PersonService bean = (PersonService) factory.createProxyInstance(new PersonServiceImpl("lucy"));
        //用户为lucy，有权限
        bean.save("abc");

        PersonService bean2 = (PersonService) factory.createProxyInstance(new PersonServiceImpl());
        //用户为null，没有权限，不输出
        bean2.save("abc");
    }
}
