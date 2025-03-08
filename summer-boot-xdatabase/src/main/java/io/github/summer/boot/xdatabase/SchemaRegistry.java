package io.github.summer.boot.xdatabase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表概要
 *
 * @author changebooks@qq.com
 */
public final class SchemaRegistry {
    /**
     * [ Table Name : the {@link Schema} instance ]
     */
    private static final Map<String, Schema> DATA = new ConcurrentHashMap<>();

    private SchemaRegistry() {
    }

    /**
     * Get Schema
     *
     * @param tableName Table Name
     * @return the {@link Schema} instance
     */
    public static Schema get(String tableName) {
        if (tableName != null) {
            return DATA.get(tableName);
        } else {
            return null;
        }
    }

    /**
     * Put Schema
     *
     * @param tableName   Table Name
     * @param tableSchema the {@link Schema} instance
     * @return previous {@link Schema} instance
     */
    public static Schema put(String tableName, Schema tableSchema) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        Preconditions.requireNonNull(tableSchema, "tableSchema must not be null, tableName: " + tableName);
        return DATA.put(tableName, tableSchema);
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
     * Remove Schema
     *
     * @param tableName Table Name
     * @return previous {@link Schema} instance
     */
    public static Schema remove(String tableName) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        return DATA.remove(tableName);
    }

}
