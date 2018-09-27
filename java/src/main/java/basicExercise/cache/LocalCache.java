package basicExercise.cache;

import com.google.common.cache.AbstractCache;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 本地缓存
 * Created by jinyancao on 3/17/17.
 */
public class LocalCache<K, V> extends AbstractCache<K, V> {
    private Map<K, V> localMap = Maps.newConcurrentMap();

    @Override
    public V getIfPresent(Object key) {
        return localMap.get(key);
    }

    @Override
    public void put(K key, V value) {
        localMap.put(key, value);
    }

    @Override
    public String toString() {
        return "LocalCache{" +
                "localMap=" + localMap +
                '}';
    }
}




