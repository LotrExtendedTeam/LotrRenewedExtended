package lotr.common.util;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    /**
     * Small helper method for doing Map.of while keeping it compatible with Java 8
     *
     * @param entries
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map<K, V> mapOf(Map.Entry<K, V>... entries) {
        Map<K, V> map = new HashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Small helper method for doing Map.of while keeping it compatible with Java 8
     *
     * @param key
     * @param value
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
