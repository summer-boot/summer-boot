package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 刷新分表配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryShardingListener implements ApplicationListener<RefreshRepositoryShardingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshRepositoryShardingListener.class);

    /**
     * the {@link ApplicationEventPublisher} instance
     */
    private final ApplicationEventPublisher publisher;

    /**
     * the {@link ShardingRefresher} instance
     */
    private final ShardingRefresher refresher;

    public RefreshRepositoryShardingListener(ApplicationEventPublisher publisher, JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(publisher, "publisher must not be null");
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.publisher = publisher;
        this.refresher = new ShardingRefresher(jdbcTemplate);
    }

    @Override
    public void onApplicationEvent(RefreshRepositoryShardingEvent event) {
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
            publisher.publishEvent(new RefreshRepositoryShardingEvent());
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
            publisher.publishEvent(new RefreshRepositoryShardingEvent(tableName));
        } catch (Throwable ex) {
            LOGGER.error("doPublishRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        try {
            ShardingRefresher refresher = getRefresher();
            refresher.refreshAll();
        } catch (Throwable ex) {
            LOGGER.error("refreshAll failed, throwable: ", ex);
        }
    }

    /**
     * Refresh
     *
     * @param tableName Table Name
     */
    public void refresh(String tableName) {
        try {
            ShardingRefresher refresher = getRefresher();
            refresher.refresh(tableName);
        } catch (Throwable ex) {
            LOGGER.error("refresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    @NotNull
    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    @NotNull
    public ShardingRefresher getRefresher() {
        return refresher;
    }

}
