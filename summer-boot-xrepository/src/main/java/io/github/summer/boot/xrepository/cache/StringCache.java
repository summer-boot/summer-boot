package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * String Cache
 *
 * @author changebooks@qq.com
 */
public class StringCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringCache.class);

    /**
     * 默认的拼接符
     * 拼接缓存名，如，"缓存名::键名"
     */
    private static final String SEPARATOR = "::";

    /**
     * 缓存模板
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 缓存名
     */
    private final String cacheName;

    public StringCache(StringRedisTemplate stringRedisTemplate, String cacheName) {
        Preconditions.requireNonNull(stringRedisTemplate, "stringRedisTemplate must not be null");

        this.stringRedisTemplate = stringRedisTemplate;

        String trimmedName = cacheName != null ? cacheName.trim() : "";
        this.cacheName = trimmedName.isEmpty() ? "" : trimmedName + SEPARATOR;
    }

    /**
     * 读取缓存
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public String get(String key) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("get failed, prefixedKey must not be null, key: {}", key);
            return null;
        }

        if (prefixedKey.isEmpty()) {
            LOGGER.error("get failed, prefixedKey must not be empty, key: {}", key);
            return null;
        }

        try {
            return stringRedisTemplate.opsForValue().get(prefixedKey);
        } catch (Throwable ex) {
            LOGGER.error("get failed, prefixedKey: {}, key: {}, throwable: ",
                    prefixedKey, key, ex);
            return null;
        }
    }

    /**
     * 设置缓存
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 缓存秒
     * @return 成功？
     */
    public boolean set(String key, String value, long timeout) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("set failed, prefixedKey must not be null, key: {}, value: {}, timeout: {}",
                    key, value, timeout);
            return false;
        }

        if (prefixedKey.isEmpty()) {
            LOGGER.error("set failed, prefixedKey must not be empty, key: {}, value: {}, timeout: {}",
                    key, value, timeout);
            return false;
        }

        try {
            stringRedisTemplate.opsForValue().set(prefixedKey, value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Throwable ex) {
            LOGGER.error("set failed, prefixedKey: {}, key: {}, value: {}, timeout: {}, throwable: ",
                    prefixedKey, key, value, timeout, ex);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     * @return 成功？
     */
    public boolean delete(String key) {
        String prefixedKey = prefixedKey(key);
        if (prefixedKey == null) {
            LOGGER.error("delete failed, prefixedKey must not be null, key: {}", key);
            return false;
        }

        if (prefixedKey.isEmpty()) {
            LOGGER.error("delete failed, prefixedKey must not be empty, key: {}", key);
            return false;
        }

        Boolean success;
        try {
            success = stringRedisTemplate.delete(prefixedKey);
        } catch (Throwable ex) {
            LOGGER.error("delete failed, prefixedKey: {}, key: {}, throwable: ",
                    prefixedKey, key, ex);
            return false;
        }

        if (success == null) {
            LOGGER.error("delete failed, success must not be null, prefixedKey: {}, key: {}",
                    prefixedKey, key);
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
     * 拼接缓存名和缓存键
     *
     * @param key 缓存键
     * @return 缓存名 + 缓存键
     */
    @Nullable
    public String prefixedKey(String key) {
        if (key == null) {
            LOGGER.error("prefixedKey failed, key must not be null");
            return null;
        }

        if (key.isEmpty()) {
            LOGGER.error("prefixedKey failed, key must not be empty");
            return null;
        }

        String cacheName = getCacheName();
        return cacheName + key;
    }

    @NotNull
    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    @NotNull
    public String getCacheName() {
        return cacheName;
    }

}
