package io.github.summer.boot.redis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Cache Template
 *
 * @author changebooks@qq.com
 */
public final class CacheTemplate extends AbstractCacheSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTemplate.class);

    /**
     * 默认的拼接符
     * 拼接键名，如，"缓存名:id"
     */
    private static final String SEPARATOR = ":";

    /**
     * 解析json
     */
    private static final ObjectMapper JSON_PARSER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * 缓存模板
     */
    private final StringRedisTemplate template;

    /**
     * 键名前缀
     */
    private final String keyPrefix;

    public CacheTemplate(boolean useCacheNamePrefix, String cacheNamePrefix,
                         StringRedisTemplate redisTemplate, String cacheName) {
        super(useCacheNamePrefix, cacheNamePrefix);

        Assert.notNull(redisTemplate, "redisTemplate must not be null");
        Assert.hasText(cacheName, "cacheName must not be empty");

        String cleanedCacheName = cacheName(cacheName);
        Assert.hasText(cleanedCacheName, "cleanedCacheName must not be empty");

        String prefixedCacheName = prefixedCacheName(cleanedCacheName);

        this.template = redisTemplate;
        this.keyPrefix = prefixedCacheName + SEPARATOR;
    }

    /**
     * 创建 {@link CacheTemplate} 实例
     *
     * @param cacheProperties 缓存配置
     * @param redisTemplate   缓存模板
     * @param cacheName       缓存名称
     * @return CacheTemplate
     */
    public static CacheTemplate create(CacheProperties cacheProperties,
                                       StringRedisTemplate redisTemplate, String cacheName) {
        Assert.notNull(cacheProperties, "cacheProperties must not be null");

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        return create(redisProperties, redisTemplate, cacheName);
    }

    /**
     * 创建 {@link CacheTemplate} 实例
     *
     * @param redisProperties 缓存配置
     * @param redisTemplate   缓存模板
     * @param cacheName       缓存名称
     * @return CacheTemplate
     */
    public static CacheTemplate create(CacheProperties.Redis redisProperties,
                                       StringRedisTemplate redisTemplate, String cacheName) {
        Assert.notNull(redisProperties, "redisProperties must not be null");

        boolean useCacheNamePrefix = redisProperties.isUseKeyPrefix();
        String cacheNamePrefix = redisProperties.getKeyPrefix();
        return create(useCacheNamePrefix, cacheNamePrefix, redisTemplate, cacheName);
    }

    /**
     * 创建 {@link CacheTemplate} 实例
     *
     * @param useCacheNamePrefix 拼接缓存名前缀？
     * @param cacheNamePrefix    缓存名前缀
     * @param redisTemplate      缓存模板
     * @param cacheName          缓存名称
     * @return CacheTemplate
     */
    public static CacheTemplate create(boolean useCacheNamePrefix, String cacheNamePrefix,
                                       StringRedisTemplate redisTemplate, String cacheName) {
        return new CacheTemplate(useCacheNamePrefix, cacheNamePrefix, redisTemplate, cacheName);
    }

    /**
     * 读取缓存
     *
     * @param key      键名
     * @param classOfV 值类型
     * @return 值对象
     */
    public <K, V> V get(K key, final Class<V> classOfV) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("get failed, prefixedKey must not be null, key: {}, classOfV: {}", key, classOfV);
            return null;
        }

        String value;
        try {
            value = template.opsForValue().get(prefixedKey);
        } catch (Throwable ex) {
            LOGGER.error("get failed, redis failed, prefixedKey: {}, key: {}, classOfV: {}, throwable: ",
                    prefixedKey, key, classOfV, ex);
            return null;
        }

        if (value == null) {
            return null;
        }

        if (value.isEmpty()) {
            LOGGER.warn("get warning, value is empty, prefixedKey: {}, key: {}, classOfV: {}",
                    prefixedKey, key, classOfV);
            return null;
        }

        try {
            return JSON_PARSER.readValue(value, classOfV);
        } catch (Throwable ex) {
            LOGGER.error("get failed, json failed, " +
                            "value: {}, classOfV: {}, prefixedKey: {}, key: {}, throwable: ",
                    value, classOfV, prefixedKey, key, ex);
            return null;
        }
    }

    /**
     * 设置缓存
     *
     * @param key     键名
     * @param obj     值对象
     * @param timeout 有效时间
     * @param unit    时间单位
     * @return 成功？
     */
    public <K, V> boolean set(K key, V obj, long timeout, TimeUnit unit) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("set failed, prefixedKey must not be null, key: {}, obj: {}, timeout: {}, unit: {}",
                    key, obj, timeout, unit);
            return false;
        }

        String value;
        try {
            value = JSON_PARSER.writeValueAsString(obj);
        } catch (Throwable ex) {
            LOGGER.error("set failed, json failed, " +
                            "obj: {}, prefixedKey: {}, key: {}, timeout: {}, unit: {}, throwable: ",
                    obj, prefixedKey, key, timeout, unit, ex);
            return false;
        }

        try {
            template.opsForValue().set(prefixedKey, value, timeout, unit);
            return true;
        } catch (Throwable ex) {
            LOGGER.error("set failed, redis failed, " +
                            "prefixedKey: {}, value: {}, timeout: {}, unit: {}, key: {}, obj: {}, throwable: ",
                    prefixedKey, value, timeout, unit, key, obj, ex);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键名
     * @return 成功？
     */
    public <K> boolean delete(K key) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("delete failed, prefixedKey must not be null, key: {}", key);
            return false;
        }

        Boolean success;
        try {
            success = template.delete(prefixedKey);
        } catch (Throwable ex) {
            LOGGER.error("delete failed, redis failed, prefixedKey: {}, key: {}, throwable: ",
                    prefixedKey, key, ex);
            return false;
        }

        if (success == null) {
            LOGGER.error("delete failed, success is null, prefixedKey: {}, key: {}", prefixedKey, key);
            return false;
        }

        if (success) {
            return true;
        } else {
            LOGGER.error("delete failed, success false, prefixedKey: {}, key: {}", prefixedKey, key);
            return false;
        }
    }

    /**
     * 拼接键名前缀和键名
     *
     * @param key 键名
     * @return 键名前缀 + 键名
     */
    public <T> String prefixedKey(T key) {
        if (key == null) {
            return null;
        }

        String keyPrefix = getKeyPrefix();
        if (keyPrefix != null) {
            return keyPrefix + key;
        } else {
            return "" + key;
        }
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

}
