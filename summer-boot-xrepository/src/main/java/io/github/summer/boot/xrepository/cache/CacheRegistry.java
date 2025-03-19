package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 寄存缓存配置
 *
 * @author changebooks@qq.com
 */
public final class CacheRegistry {
    /**
     * [ Table Name : Cache Time ]
     */
    private static final Map<String, Integer> DATA = new ConcurrentHashMap<>();

    private CacheRegistry() {
    }

    /**
     * Get Schema
     *
     * @param tableName Table Name
     * @return Cache Time
     */
    public static Integer get(String tableName) {
        if (tableName != null) {
            return DATA.get(tableName);
        } else {
            return null;
        }
    }

    /**
     * Put Schema
     *
     * @param tableName Table Name
     * @param cacheTime Cache Time
     * @return Previous Cache Time
     */
    public static Integer put(String tableName, Integer cacheTime) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        return DATA.put(tableName, cacheTime);
    }

    /**
     * Remove Schema
     *
     * @param tableName Table Name
     * @return Previous Cache Time
     */
    public static Integer remove(String tableName) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        return DATA.remove(tableName);
    }

    /**
     * Contains Schema ?
     *
     * @param tableName Table Name
     * @return contains ? true : false
     */
    public static boolean contains(String tableName) {
        if (tableName != null) {
            return DATA.containsKey(tableName);
        } else {
            return false;
        }
    }

    /**
     * Get Keys
     *
     * @return [ Key Name ]
     */
    @NotNull
    public static Set<String> keySet() {
        return DATA.keySet();
    }

}
