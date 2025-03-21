package io.github.summer.boot.xrepository;

import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.cache.CacheRegistry;
import io.github.summer.boot.xrepository.cache.ValueCache;
import io.github.summer.boot.xrepository.logger.LogCache;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * Cache
 *
 * @author changebooks@qq.com
 */
public class Cache {
    /**
     * the {@link ValueCache} instance
     */
    private final ValueCache valueCache;

    /**
     * the {@link LogCache} instance
     */
    private LogCache logWriter;

    public Cache(ValueCache valueCache) {
        Preconditions.requireNonNull(valueCache, "valueCache must not be null");

        this.valueCache = valueCache;
    }

    /**
     * Get
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> get(@NotNull String tableName, Value keyValue) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime <= 0) {
            return null;
        }

        ValueCache valueCache = getValueCache();
        Map<String, Value> result = valueCache.get(tableName, keyValue);

        writeLogGet(tableName, keyValue, result);
        return result;
    }

    /**
     * Set
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     * @param values    [ Column Name : Column Value ]
     */
    public void set(@NotNull String tableName, Value keyValue, Map<String, Value> values) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime <= 0) {
            return;
        }

        ValueCache valueCache = getValueCache();
        boolean result = valueCache.set(tableName, keyValue, values, cacheTime);

        writeLogSet(tableName, keyValue, values, result);
    }

    /**
     * Delete
     *
     * @param tableName Table Name
     * @param keyValue  Key Value
     */
    public void delete(@NotNull String tableName, Value keyValue) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime <= 0) {
            return;
        }

        ValueCache valueCache = getValueCache();
        boolean result = valueCache.delete(tableName, keyValue);

        writeLogDelete(tableName, keyValue, result);
    }

    /**
     * Get Cache Time
     *
     * @param tableName Table Name
     * @return Cache Time
     */
    public int getCacheTime(@NotNull String tableName) {
        Integer cacheTime = CacheRegistry.get(tableName);
        return cacheTime != null ? cacheTime : 0;
    }

    @NotNull
    public ValueCache getValueCache() {
        return valueCache;
    }

    protected void writeLogGet(String tableName, Value keyValue, Map<String, Value> result) {
        try {
            LogCache logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.get(tableName, keyValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSet(String tableName, Value keyValue, Map<String, Value> values, boolean result) {
        try {
            LogCache logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.set(tableName, keyValue, values, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogDelete(String tableName, Value keyValue, boolean result) {
        try {
            LogCache logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.delete(tableName, keyValue, result);
            }
        } catch (Throwable ignored) {
        }
    }

    @Nullable
    public LogCache getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(@Nullable LogCache logWriter) {
        this.logWriter = logWriter;
    }

}
