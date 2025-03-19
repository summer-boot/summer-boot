package io.github.summer.boot.xrepository.key;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 寄存键名配置
 *
 * @author changebooks@qq.com
 */
public final class KeyRegistry {
    /**
     * [ Table Name : Key Name ]
     */
    private static final Map<String, String> DATA = new ConcurrentHashMap<>();

    private KeyRegistry() {
    }

    /**
     * Get Schema
     *
     * @param tableName Table Name
     * @return Key Name
     */
    public static String get(String tableName) {
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
     * @param keyName   Key Name
     * @return Previous Key Name
     */
    public static String put(String tableName, String keyName) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        return DATA.put(tableName, keyName);
    }

    /**
     * Remove Schema
     *
     * @param tableName Table Name
     * @return Previous Key Name
     */
    public static String remove(String tableName) {
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
