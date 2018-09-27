package proxy.cglib;

/**
 * Created by jinyancao on 4/14/16.
 */
public class BookServiceFactory {
    private static BookServiceBean service = new BookServiceBean();
    private BookServiceFactory() {
    }
    public static BookServiceBean getInstance() {
        return service;
    }
}
