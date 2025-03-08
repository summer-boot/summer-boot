package io.github.summer.boot.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * 统一异常处理
 * 默认处理方式，错误日志
 *
 * @author changebooks@qq.com
 */
public class CacheExceptionHandler implements CacheErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheExceptionHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException ex, Cache cache, Object key) {
        String cacheName = cache.getName();
        LOGGER.error("CacheExceptionHandler handleCacheGetError, cacheName: {}, key: {}, throwable: ",
                cacheName, key, ex);
    }

    @Override
    public void handleCachePutError(RuntimeException ex, Cache cache, Object key, Object value) {
        String cacheName = cache.getName();
        LOGGER.error("CacheExceptionHandler handleCachePutError, cacheName: {}, key: {}, value: {}, throwable: ",
                cacheName, key, value, ex);
    }

    @Override
    public void handleCacheEvictError(RuntimeException ex, Cache cache, Object key) {
        String cacheName = cache.getName();
        LOGGER.error("CacheExceptionHandler handleCacheEvictError, cacheName: {}, key: {}, throwable: ",
                cacheName, key, ex);
    }

    @Override
    public void handleCacheClearError(RuntimeException ex, Cache cache) {
        String cacheName = cache.getName();
        LOGGER.error("CacheExceptionHandler handleCacheClearError, cacheName: {}, throwable: ",
                cacheName, ex);
    }

}
