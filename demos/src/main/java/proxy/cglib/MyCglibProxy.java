package proxy.cglib;

import net.sf.cglib.proxy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 创建bean对象的时候，设置代理类，代理的过滤方法类。
 * Created by jinyancao on 2016/04/22/21/27
 */

public class MyCglibProxy implements MethodInterceptor {
    private Logger log = LoggerFactory.getLogger(MyCglibProxy.class);
    public Enhancer enhancer = new Enhancer();
    private String name;

    public MyCglibProxy(String name) {
        this.name = name;
    }

    /**
     * 根据class对象创建该对象的代理对象
     * 1、设置父类；2、设置回调
     * 本质：动态创建了一个class对象的子类
     *
     * @param cls
     * @return
     */
    public Object getDaoBean(Class cls) {
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    /**
     * 通过代理生成实例对象
     * @param myProxy
     * @return
     */
    public static BookServiceBean getProxyInstance(MyCglibProxy myProxy) {
        Enhancer en = new Enhancer();
        //进行代理
        en.setSuperclass(BookServiceBean.class);
        en.setCallback(myProxy);
        //生成代理实例
        return (BookServiceBean) en.create();

    }


    /**
     * 添加过滤方法的过滤类
     * @param myProxy
     * @return
     */
    public static BookServiceBean getAuthInstanceByFilter(MyCglibProxy myProxy){
        Enhancer en = new Enhancer();
        en.setSuperclass(BookServiceBean.class);
        en.setCallbacks(new Callback[]{myProxy, NoOp.INSTANCE});
        en.setCallbackFilter(new MyProxyFilter());
        return (BookServiceBean)en.create();
    }


    public Object intercept(Object object, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        log.info("调用的方法是：" + method.getName());
        //用户进行判断
        if (!"张三".equals(name)) {
            System.out.println("你没有权限！");
            return null;
        }
        Object result = methodProxy.invokeSuper(object, args);

        return result;
    }


}