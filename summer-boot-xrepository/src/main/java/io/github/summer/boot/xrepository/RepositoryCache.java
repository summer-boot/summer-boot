package io.github.summer.boot.xrepository;

import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.cache.StringCache;
import io.github.summer.boot.xrepository.cache.ValueCache;
import io.github.summer.boot.xrepository.schema.RepositorySchema;
import io.github.summer.boot.xrepository.schema.RepositorySchemaRegistry;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

/**
 * Repository Cache
 *
 * @author changebooks@qq.com
 */
public class RepositoryCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCache.class);

    /**
     * 缓存模板
     */
    private final ValueCache valueCache;

    public RepositoryCache(StringRedisTemplate stringRedisTemplate, String cacheName) {
        StringCache stringCache = new StringCache(stringRedisTemplate, cacheName);
        this.valueCache = new ValueCache(stringCache);
    }

    /**
     * 读取缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> get(String tableName, Value keyValue) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime > 0) {
            ValueCache valueCache = getValueCache();
            return valueCache.get(tableName, keyValue);
        } else {
            return null;
        }
    }

    /**
     * 设置缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @param values    [ Column Name : Column Value ]
     * @return 成功？
     */
    public boolean set(String tableName, Value keyValue, Map<String, Value> values) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime > 0) {
            ValueCache valueCache = getValueCache();
            return valueCache.set(tableName, keyValue, values, cacheTime);
        } else {
            return true;
        }
    }

    /**
     * 删除缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return 成功？
     */
    public boolean delete(String tableName, Value keyValue) {
        int cacheTime = getCacheTime(tableName);
        if (cacheTime > 0) {
            ValueCache valueCache = getValueCache();
            return valueCache.delete(tableName, keyValue);
        } else {
            return true;
        }
    }

    /**
     * 获取缓存时间
     *
     * @param tableName Table Name
     * @return 缓存秒数
     */
    protected int getCacheTime(String tableName) {
        if (tableName == null) {
            LOGGER.error("getCacheTime failed, tableName must not be null");
            return 0;
        }

        RepositorySchema repositorySchema = RepositorySchemaRegistry.get(tableName);
        if (repositorySchema != null) {
            return repositorySchema.getCacheTime();
        } else {
            return 0;
        }
    }

    @NotNull
    public ValueCache getValueCache() {
        return valueCache;
    }

}
