package io.github.summer.boot.xrepository.key;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 刷新键名配置
 *
 * @author changebooks@qq.com
 */
public class KeyRefresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyRefresher.class);

    /**
     * the {@link KeyQuery} instance
     */
    private final KeyQuery keyQuery;

    public KeyRefresher(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.keyQuery = new KeyQuery(jdbcTemplate);
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        KeyQuery keyQuery = getKeyQuery();
        Map<String, String> data = keyQuery.selectAll();
        if (data == null) {
            LOGGER.error("refreshAll failed, data must not be null");
            return;
        }

        List<String> removeKeys = KeyRegistry.getKeys()
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

        KeyQuery keyQuery = getKeyQuery();
        String keyName = keyQuery.selectOne(trimmedName);

        KeyRegistry.put(trimmedName, keyName);
        LOGGER.info("refresh trace, tableName: {}, keyName: {}", trimmedName, keyName);
    }

    /**
     * Put All
     *
     * @param data [ Table Name : Key Name ]
     */
    public void putAll(Map<String, String> data) {
        if (data != null) {
            data.entrySet().stream()
                    .filter(Objects::nonNull)
                    .filter(entry -> Objects.nonNull(entry.getKey()))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        String tableName = key.trim();
                        if (!tableName.isEmpty()) {
                            String keyName = entry.getValue();
                            KeyRegistry.put(tableName, keyName);
                            LOGGER.info("putAll trace, tableName: {}, keyName: {}", tableName, keyName);
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
                        KeyRegistry.remove(tableName);
                        LOGGER.info("removeAll trace, tableName: {}", tableName);
                    });
        } else {
            LOGGER.error("removeAll failed, tableNames must not be null");
        }
    }

    @NotNull
    public KeyQuery getKeyQuery() {
        return keyQuery;
    }

}
