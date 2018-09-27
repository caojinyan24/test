package basicExercise.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by jinyan on 11/19/17 2:53 PM.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        ServiceLoader<Base> sl = ServiceLoader.load(Base.class);//根据类的fullname+前缀，查找资源文件
        Iterator<Base> s = sl.iterator();
        if (s.hasNext()) {
            Base ss = s.next();
            ss.print();
        }
    }
}
