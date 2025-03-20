package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 刷新分表配置
 *
 * @author changebooks@qq.com
 */
public class ShardingRefresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardingRefresher.class);

    /**
     * the {@link ShardingQuery} instance
     */
    private final ShardingQuery shardingQuery;

    public ShardingRefresher(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.shardingQuery = new ShardingQuery(jdbcTemplate);
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        ShardingQuery shardingQuery = getShardingQuery();
        Map<String, Integer> data = shardingQuery.selectAll();
        if (data == null) {
            LOGGER.error("refreshAll failed, data must not be null");
            return;
        }

        List<String> removeKeys = ShardingRegistry.getKeys()
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

        ShardingQuery shardingQuery = getShardingQuery();
        Integer tableSize = shardingQuery.selectOne(trimmedName);

        ShardingRegistry.put(trimmedName, tableSize);
        LOGGER.info("refresh trace, tableName: {}, tableSize: {}", trimmedName, tableSize);
    }

    /**
     * Put All
     *
     * @param data [ Table Name : Table Size ]
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
                            Integer tableSize = entry.getValue();
                            ShardingRegistry.put(tableName, tableSize);
                            LOGGER.info("putAll trace, tableName: {}, tableSize: {}", tableName, tableSize);
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
                        ShardingRegistry.remove(tableName);
                        LOGGER.info("removeAll trace, tableName: {}", tableName);
                    });
        } else {
            LOGGER.error("removeAll failed, tableNames must not be null");
        }
    }

    @NotNull
    public ShardingQuery getShardingQuery() {
        return shardingQuery;
    }

}
