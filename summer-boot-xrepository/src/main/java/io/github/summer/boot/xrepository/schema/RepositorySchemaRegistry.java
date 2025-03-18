package io.github.summer.boot.xrepository.schema;

import io.github.summer.boot.sql.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 寄存数仓概要
 *
 * @author changebooks@qq.com
 */
public final class RepositorySchemaRegistry {
    /**
     * [ Table Name : the {@link RepositorySchema} instance ]
     */
    private static final Map<String, RepositorySchema> DATA = new ConcurrentHashMap<>();

    private RepositorySchemaRegistry() {
    }

    /**
     * Get Schema
     *
     * @param tableName Table Name
     * @return the {@link RepositorySchema} instance
     */
    public static RepositorySchema get(String tableName) {
        if (tableName != null) {
            return DATA.get(tableName);
        } else {
            return null;
        }
    }

    /**
     * Put Schema
     *
     * @param repositorySchema the {@link RepositorySchema} instance
     * @return previous {@link RepositorySchema} instance
     */
    public static RepositorySchema put(RepositorySchema repositorySchema) {
        Preconditions.requireNonNull(repositorySchema, "repositorySchema must not be null");

        String tableName = repositorySchema.getTableName();
        return DATA.put(tableName, repositorySchema);
    }

    /**
     * Put Schema
     *
     * @param tableName        Table Name
     * @param repositorySchema the {@link RepositorySchema} instance
     * @return previous {@link RepositorySchema} instance
     */
    public static RepositorySchema put(String tableName, RepositorySchema repositorySchema) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");
        Preconditions.requireNonNull(repositorySchema, "repositorySchema must not be null, tableName: " + tableName);
        return DATA.put(tableName, repositorySchema);
    }

    /**
     * Remove Schema
     *
     * @param tableName Table Name
     * @return previous {@link RepositorySchema} instance
     */
    public static RepositorySchema remove(String tableName) {
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

}
