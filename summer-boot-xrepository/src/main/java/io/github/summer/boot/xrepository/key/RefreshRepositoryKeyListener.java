package io.github.summer.boot.xrepository.key;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 刷新键名配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryKeyListener implements ApplicationListener<RefreshRepositoryKeyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshRepositoryKeyListener.class);

    /**
     * the {@link ApplicationEventPublisher} instance
     */
    private final ApplicationEventPublisher publisher;

    /**
     * the {@link KeyRefresher} instance
     */
    private final KeyRefresher refresher;

    public RefreshRepositoryKeyListener(ApplicationEventPublisher publisher, JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(publisher, "publisher must not be null");
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.publisher = publisher;
        this.refresher = new KeyRefresher(jdbcTemplate);
    }

    @Override
    public void onApplicationEvent(RefreshRepositoryKeyEvent event) {
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
            publisher.publishEvent(new RefreshRepositoryKeyEvent());
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
            publisher.publishEvent(new RefreshRepositoryKeyEvent(tableName));
        } catch (Throwable ex) {
            LOGGER.error("doPublishRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * Refresh All
     */
    public void refreshAll() {
        try {
            KeyRefresher refresher = getRefresher();
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
            KeyRefresher refresher = getRefresher();
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
    public KeyRefresher getRefresher() {
        return refresher;
    }

}
