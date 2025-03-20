package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 刷新缓存配置
 *
 * @author changebooks@qq.com
 */
public class CacheRefresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheRefresher.class);

    /**
     * the {@link CacheQuery} instance
     */
    private final CacheQuery cacheQuery;

    public CacheRefresher(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.cacheQuery = new CacheQuery(jdbcTemplate);
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        CacheQuery cacheQuery = getCacheQuery();
        Map<String, Integer> data = cacheQuery.selectAll();
        if (data == null) {
            LOGGER.error("refreshAll failed, data must not be null");
            return;
        }

        List<String> removeKeys = CacheRegistry.getKeys()
                .stream()
                .filter(tableName -> !data.containsKey(tableName))
                .toList();

        removeAll(removeKeys);
        putAll(data);
    }

    /**
     * Refresh
     *
     * @param tableName Table Name
     */
    public void refresh(String tableName) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");

        String trimmedName = tableName.trim();
        Preconditions.requireNonEmpty(trimmedName, "tableName must not be empty");

        CacheQuery cacheQuery = getCacheQuery();
        Integer cacheTime = cacheQuery.selectOne(trimmedName);

        CacheRegistry.put(trimmedName, cacheTime);
        LOGGER.info("refresh trace, tableName: {}, cacheTime: {}", trimmedName, cacheTime);
    }

    /**
     * Put All
     *
     * @param data [ Table Name : Cache Time ]
     */
    public void putAll(Map<String, Integer> data) {
        if (data != null) {
            data.entrySet().stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> Objects.nonNull(entry.getKey()))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        String tableName = key.trim();
                        if (!tableName.isEmpty()) {
                            Integer cacheTime = entry.getValue();
                            CacheRegistry.put(tableName, cacheTime);
                            LOGGER.info("putAll trace, tableName: {}, cacheTime: {}", tableName, cacheTime);
                        }
                    });
        } else {
            LOGGER.error("putAll failed, data must not be null");
        }
    }

    /**
     * Remove All
     *
     * @param tableNames [ Table Name ]
     */
    public void removeAll(List<String> tableNames) {
        if (tableNames != null) {
            tableNames.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(tableName -> !tableName.isEmpty())
                    .forEach(tableName -> {
                        CacheRegistry.remove(tableName);
                        LOGGER.info("removeAll trace, tableName: {}", tableName);
                    });
        } else {
            LOGGER.error("removeAll failed, tableNames must not be null");
        }
    }

    @NotNull
    public CacheQuery getCacheQuery() {
        return cacheQuery;
    }

}
