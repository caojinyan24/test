package basicExercise.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.graph.Graph;
import com.google.common.math.Quantiles;

import java.security.Key;
import java.util.Map;

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

