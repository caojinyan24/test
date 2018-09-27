package proxy.cglib;


import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 设置对哪个方法执行代理，跟代理类配合使用
 * Created by jinyancao on 2016/04/22/21/37
 */
public class MyProxyFilter implements CallbackFilter {
    public int accept(Method method) {
        if (!"query".equalsIgnoreCase(method.getName()))
            return 0;
        return 1;
    }
}
