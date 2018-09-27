package proxy.cglib;

/**
 * Created by jinyancao on 2016/04/22/21/40
 */
public class Main {
    public static void main(String[] args) {
        BookServiceBean service = MyCglibProxy.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
        service.create();
        BookServiceBean service1 = MyCglibProxy.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
        service1.query();
        BookServiceBean service2 = MyCglibProxy.getProxyInstance(new MyCglibProxy("jhon"));
        service2.query();
    }
}
