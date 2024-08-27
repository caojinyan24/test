package basicExercise.cache;

/**
 * 本地缓存demo
 * Created by jinyancao on 3/17/17.
 */
public class CacheTestMain {
    LocalCache localCache = new LocalCache<Integer, Object>();

    public static void main(String[] args) {
        CacheTestMain cacheTestMain = new CacheTestMain();
        cacheTestMain.test();
    }

    private void test() {
        for (Integer i = 0; i < 10; i++) {
            localCache.put(i, i * 10);
            System.out.println(String.format("cacheTest put key:%d,value:%d", i, i * 10));
        }
        System.out.println(localCache);
    }


}

