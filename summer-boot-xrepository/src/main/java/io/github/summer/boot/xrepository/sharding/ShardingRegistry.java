package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 寄存分表配置
 *
 * @author changebooks@qq.com
 */
public final class ShardingRegistry {
    /**
     * [ Table Name : Table Size ]
     */
    private static final Map<String, Integer> DATA = new ConcurrentHashMap<>();

    private ShardingRegistry() {
    }

    /**
     * Get Schema
     *
     * @param tableName Table Name
     * @return Table Size
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
     * @param tableSize Table Size
     * @return Previous Table Size
     */
    public static Integer put(String tableName, Integer tableSize) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        return DATA.put(tableName, tableSize);
    }

    /**
     * Remove Schema
     *
     * @param tableName Table Name
     * @return Previous Table Size
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
