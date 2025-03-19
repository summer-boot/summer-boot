package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

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
