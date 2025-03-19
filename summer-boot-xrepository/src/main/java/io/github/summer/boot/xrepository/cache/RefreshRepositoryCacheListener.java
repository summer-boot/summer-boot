package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.xrepository.JsonParser;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 刷新缓存配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryCacheListener implements ApplicationListener<RefreshRepositoryCacheEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshRepositoryCacheListener.class);

    /**
     * the {@link ApplicationEventPublisher} instance
     */
    private final ApplicationEventPublisher publisher;

    /**
     * the {@link CacheQuery} instance
     */
    private final CacheQuery cacheQuery;

    public RefreshRepositoryCacheListener(ApplicationEventPublisher publisher, JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(publisher, "publisher must not be null");
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.publisher = publisher;
        this.cacheQuery = new CacheQuery(jdbcTemplate);
    }

    @Override
    public void onApplicationEvent(RefreshRepositoryCacheEvent event) {
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
            publisher.publishEvent(new RefreshRepositoryCacheEvent());
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
            publisher.publishEvent(new RefreshRepositoryCacheEvent(tableName));
        } catch (Throwable ex) {
            LOGGER.error("doPublishRefresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * 刷新全部
     */
    public void refreshAll() {
        try {
            doRefreshAll();
        } catch (Throwable ex) {
            LOGGER.error("refreshAll failed, throwable: ", ex);
        }
    }

    /**
     * 刷新
     *
     * @param tableName 表名
     */
    public void refresh(String tableName) {
        try {
            doRefresh(tableName);
        } catch (Throwable ex) {
            LOGGER.error("refresh failed, tableName: {}, throwable: ", tableName, ex);
        }
    }

    /**
     * 执行刷新全部
     */
    protected void doRefreshAll() {
        CacheQuery cacheQuery = getCacheQuery();
        Map<String, Integer> data = cacheQuery.selectAll();
        Preconditions.requireNonNull(data, "data must not be null");

        List<String> keys = CacheRegistry.getKeys();
        for (String key : keys) {
            if (!data.containsKey(key)) {
                data.put(key, null);
            }
        }

        CacheRegistry.putAll(data);
        LOGGER.info("doRefresh trace, data: {}", JsonParser.toJson(data));
    }

    /**
     * 执行刷新
     *
     * @param rawTableName 原始表名
     */
    protected void doRefresh(String rawTableName) {
        Preconditions.requireNonNull(rawTableName, "tableName must not be null");

        String tableName = rawTableName.trim();
        Preconditions.requireNonEmpty(tableName, "tableName must not be empty");

        CacheQuery cacheQuery = getCacheQuery();
        Integer cacheTime = cacheQuery.selectOne(tableName);
        CacheRegistry.put(tableName, cacheTime);

        LOGGER.info("doRefresh trace, tableName: {}, cacheTime: {}", tableName, cacheTime);
    }

    @NotNull
    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }

    @NotNull
    public CacheQuery getCacheQuery() {
        return cacheQuery;
    }

}
