package io.github.summer.boot.xrepository;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 刷数仓概要
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositorySchemaListener implements ApplicationListener<RefreshRepositorySchemaEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshRepositorySchemaListener.class);

    /**
     * the {@link ApplicationEventPublisher} instance
     */
    private final ApplicationEventPublisher publisher;

    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    public RefreshRepositorySchemaListener(ApplicationEventPublisher publisher, JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(publisher, "publisher must not be null");
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.publisher = publisher;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(RefreshRepositorySchemaEvent event) {
        String tableName = event.getTableName();
        if (tableName == null) {
            refreshAll();
        } else {
            refresh(tableName);
        }
    }

    /**
     * Publish Refresh All
     */
    public void publishRefreshAll() {
        Thread.ofVirtual().start(this::doPublishRefreshAll);
    }

    /**
     * Publish Refresh
     *
     * @param tableName Table Name
     */
    public void publishRefresh(String tableName) {
        Thread.ofVirtual().start(() -> doPublishRefresh(tableName));
    }

    /**
     * Publish Refresh All
     */
    protected void doPublishRefreshAll() {
        try {
            ApplicationEventPublisher publisher = getPublisher();
            publisher.publishEvent(new RefreshRepositorySchemaEvent());
        } catch (Throwable ex) {
            LOGGER.error("doPublishRefreshAll failed, throwable: ", ex);
        }
    }

    /**
     * Publish Refresh
     *
     * @param tableName Table Name
     */
    protected void doPublishRefresh(String tableName) {
        try {
            ApplicationEventPublisher publisher = getPublisher();
            publisher.publishEvent(new RefreshRepositorySchemaEvent(tableName));
        } catch (Throwable ex) {
            LOGGER.error("doPublishRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        List<RepositorySchema> list = selectList();
        if (list == null) {
            return;
        }

        for (RepositorySchema repositorySchema : list) {
            if (repositorySchema == null) {
                continue;
            }

            RepositorySchemaRegistry.put(repositorySchema);
            LOGGER.info("refreshAll trace, repositorySchema: {}", repositorySchema);
        }
    }

    /**
     * Refresh
     *
     * @param tableName Table Name
     */
    public void refresh(String tableName) {
        try {
            doRefresh(tableName);
        } catch (Throwable ex) {
            LOGGER.error("doRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Refresh Schema
     *
     * @param tableName Table Name
     */
    protected void doRefresh(String tableName) {
        Preconditions.requireNonNull(tableName, "tableName must not be null");

        String trimmedName = tableName.trim();
        Preconditions.requireNonEmpty(trimmedName, "tableName must not be empty");

        RepositorySchema repositorySchema = selectOne(trimmedName);
        if (repositorySchema == null) {
            LOGGER.error("doRefresh failed, unsupported tableName: {}", trimmedName);
            return;
        }

        RepositorySchemaRegistry.put(repositorySchema);
        LOGGER.info("doRefresh trace, tableName: {}, repositorySchema: {}", trimmedName, repositorySchema);
    }

    /**
     * SELECT LIST
     *
     * @return [ the {@link RepositorySchema} instance ]
     */
    public List<RepositorySchema> selectList() {
        try {
            return jdbcTemplate.query
                    (
                            "SELECT table_name, key_name, table_size, cache_time FROM xrepository_schema",
                            rs -> {
                                List<RepositorySchema> data = new ArrayList<>();

                                while (rs.next()) {
                                    RepositorySchema row = rowMapper(rs);
                                    data.add(row);
                                }

                                return data;
                            }
                    );
        } catch (Throwable ex) {
            LOGGER.error("selectList failed, throwable: ", ex);
            return null;
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName Table Name
     * @return the {@link RepositorySchema} instance
     */
    public RepositorySchema selectOne(@NotNull String tableName) {
        try {
            return jdbcTemplate.query
                    (
                            "SELECT table_name, key_name, table_size, cache_time FROM xrepository_schema WHERE table_name = ?",
                            ps -> ps.setString(1, tableName),
                            rs -> {
                                if (rs.next()) {
                                    return rowMapper(rs);
                                } else {
                                    return null;
                                }
                            }
                    );
        } catch (Throwable ex) {
            LOGGER.error("selectOne failed, tableName: {}, throwable: ", tableName, ex);
            return null;
        }
    }

    /**
     * Row Mapper
     *
     * @param rs the {@link ResultSet} instance
     * @return the {@link RepositorySchema} instance
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    public RepositorySchema rowMapper(@NotNull ResultSet rs) throws SQLException {
        RepositorySchema result = new RepositorySchema();

        String tableName = rs.getString("table_name");
        result.setTableName(tableName);

        String keyName = rs.getString("key_name");
        result.setKeyName(keyName);

        int tableSize = rs.getInt("table_size");
        result.setTableSize(tableSize);

        int tableSizeMask = tableSize - 1;
        result.setTableSizeMask(tableSizeMask);

        if (tableSize > 0 && tableSizeMask > 0) {
            boolean tableSizePower2 = (tableSize & tableSizeMask) == 0;
            result.setTableSizePower2(tableSizePower2);
        }

        int cacheTime = rs.getInt("cache_time");
        result.setCacheTime(cacheTime);

        return result;
    }

    @NotNull
    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    @NotNull
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
