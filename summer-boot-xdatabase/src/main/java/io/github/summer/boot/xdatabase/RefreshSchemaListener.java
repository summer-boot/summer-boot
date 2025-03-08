package io.github.summer.boot.xdatabase;

import io.github.summer.boot.xdatabase.schema.TableNameReader;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Refresh Schema Listener
 *
 * @author changebooks@qq.com
 */
public class RefreshSchemaListener implements ApplicationListener<RefreshSchemaEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshSchemaListener.class);

    /**
     * the {@link ApplicationEventPublisher} instance
     */
    private final ApplicationEventPublisher publisher;

    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    public RefreshSchemaListener(ApplicationEventPublisher publisher, JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(publisher, "publisher must not be null");
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.publisher = publisher;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(RefreshSchemaEvent event) {
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
            publisher.publishEvent(new RefreshSchemaEvent());
        } catch (Throwable ex) {
            LOGGER.error("doPublishEvent failed, throwable: ", ex);
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
            publisher.publishEvent(new RefreshSchemaEvent(tableName));
        } catch (Throwable ex) {
            LOGGER.error("doPublishEvent failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        Connection connection = doGetConnection();
        if (connection == null) {
            LOGGER.error("refreshAll failed, connection must not be null");
            return;
        }

        List<String> tableNames = doGetTableNames(connection);
        if (tableNames == null) {
            LOGGER.error("refreshAll failed, tableNames must not be null");
            return;
        }

        for (String tableName : tableNames) {
            if (tableName != null) {
                doRefresh(connection, tableName);
            }
        }
    }

    /**
     * Refresh
     *
     * @param tableName Table Name
     */
    public void refresh(String tableName) {
        Connection connection = doGetConnection();
        if (connection != null) {
            doRefresh(connection, tableName);
        } else {
            LOGGER.error("refresh failed, connection must not be null, tableName: {}", tableName);
        }
    }

    /**
     * Refresh Schema
     *
     * @param connection the {@link Connection} instance
     * @param tableName  Table Name
     */
    protected void doRefresh(Connection connection, String tableName) {
        try {
            Preconditions.requireNonNull(connection, "connection must not be null, tableName: " + tableName);
            Preconditions.requireNonNull(tableName, "tableName must not be null");

            SchemaReader.registry(connection, tableName);
            LOGGER.info("doRefresh trace, tableName: {}", tableName);
        } catch (Throwable ex) {
            LOGGER.error("doRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Get Table Names
     *
     * @param connection the {@link Connection} instance
     * @return [ Table Name ]
     */
    @Nullable
    protected List<String> doGetTableNames(Connection connection) {
        try {
            Preconditions.requireNonNull(connection, "connection must not be null");
            return TableNameReader.read(connection);
        } catch (Throwable ex) {
            LOGGER.error("doGetTableNames failed, throwable: ", ex);
            return null;
        }
    }

    /**
     * Get Connection
     *
     * @return the {@link Connection} instance
     */
    @Nullable
    protected Connection doGetConnection() {
        try {
            DataSource dataSource = doGetDataSource();
            Preconditions.requireNonNull(dataSource, "dataSource must not be null");

            return dataSource.getConnection();
        } catch (Throwable ex) {
            LOGGER.error("doGetConnection failed, throwable: ", ex);
            return null;
        }
    }

    /**
     * Get DataSource
     *
     * @return the {@link DataSource} instance
     */
    @Nullable
    protected DataSource doGetDataSource() {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        return jdbcTemplate.getDataSource();
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
