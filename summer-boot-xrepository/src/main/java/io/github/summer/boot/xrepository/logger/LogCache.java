package io.github.summer.boot.xrepository.logger;

import io.github.summer.boot.value.Value;

import java.util.Map;

/**
 * Log Cache
 *
 * @author changebooks@qq.com
 */
public interface LogCache {
    /**
     * Get
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @param result    [ Column Name : Column Value ]
     */
    void get(String tableName, Value keyValue, Map<String, Value> result);

    /**
     * Set
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @param values    [ Column Name : Column Value ]
     * @param result    success ?
     */
    void set(String tableName, Value keyValue, Map<String, Value> values, boolean result);

    /**
     * Delete
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @param result    success ?
     */
    void delete(String tableName, Value keyValue, boolean result);

}
