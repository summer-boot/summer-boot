package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.JsonParser;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Value Cache
 *
 * @author changebooks@qq.com
 */
public class ValueCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueCache.class);

    /**
     * 默认的拼接符
     * 拼接键名，如，"缓存名:id"
     */
    private static final String SEPARATOR = ":";

    /**
     * the {@link StringCache} instance
     */
    private final StringCache stringCache;

    public ValueCache(StringCache stringCache) {
        Preconditions.requireNonNull(stringCache, "stringCache must not be null");

        this.stringCache = stringCache;
    }

    /**
     * 读取缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> get(String tableName, Value keyValue) {
        String key = joinKey(tableName, keyValue);
        if (key == null) {
            LOGGER.error("get failed, key must not be null, tableName: {}, keyValue: {}",
                    tableName, keyValue);
            return null;
        }

        if (key.isEmpty()) {
            LOGGER.error("get failed, key must not be empty, tableName: {}, keyValue: {}",
                    tableName, keyValue);
            return null;
        }

        StringCache stringCache = getStringCache();
        String json = stringCache.get(key);
        if (json == null) {
            return null;
        }

        if (json.isEmpty()) {
            LOGGER.warn("get warning, json is empty, key: {}, tableName: {}, keyValue: {}",
                    key, tableName, keyValue);
            return null;
        }

        return fromJson(json);
    }

    /**
     * 设置缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @param values    [ Column Name : Column Value ]
     * @param timeout   缓存秒
     * @return 成功？
     */
    public boolean set(String tableName, Value keyValue, Map<String, Value> values, long timeout) {
        if (values == null) {
            LOGGER.error("set failed, values must not be null, tableName: {}, keyValue: {}, timeout: {}",
                    tableName, keyValue, timeout);
            return delete(tableName, keyValue);
        }

        String key = joinKey(tableName, keyValue);
        if (key == null) {
            LOGGER.error("set failed, key must not be null, tableName: {}, keyValue: {}, timeout: {}",
                    tableName, keyValue, timeout);
            return false;
        }

        if (key.isEmpty()) {
            LOGGER.error("set failed, key must not be empty, tableName: {}, keyValue: {}, timeout: {}",
                    tableName, keyValue, timeout);
            return false;
        }

        String json = toJson(values);
        if (json == null) {
            LOGGER.error("set failed, json must not be null, key: {}, tableName: {}, keyValue: {}, timeout: {}",
                    key, tableName, keyValue, timeout);
            return false;
        }

        StringCache stringCache = getStringCache();
        return stringCache.set(key, json, timeout);
    }

    /**
     * 删除缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return 成功？
     */
    public boolean delete(String tableName, Value keyValue) {
        String key = joinKey(tableName, keyValue);
        if (key == null) {
            LOGGER.error("delete failed, key must not be null, tableName: {}, keyValue: {}",
                    tableName, keyValue);
            return false;
        }

        if (key.isEmpty()) {
            LOGGER.error("delete failed, key must not be empty, tableName: {}, keyValue: {}",
                    tableName, keyValue);
            return false;
        }

        StringCache stringCache = getStringCache();
        return stringCache.delete(key);
    }

    /**
     * 拼接表名和键名
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return 表名 + 键名
     */
    @Nullable
    public String joinKey(String tableName, Value keyValue) {
        if (keyValue != null) {
            String keyName = keyValue.toString();
            return joinKey(tableName, keyName);
        } else {
            LOGGER.error("joinKey failed, keyValue must not be null, tableName: {}", tableName);
            return null;
        }
    }

    /**
     * 拼接表名和键名
     *
     * @param tableName 表名
     * @param keyName   键名
     * @return 表名 + 键名
     */
    @Nullable
    public String joinKey(String tableName, String keyName) {
        if (tableName == null) {
            LOGGER.error("joinKey failed, tableName must not be null, keyName: {}", keyName);
            return null;
        }

        if (tableName.isEmpty()) {
            LOGGER.error("joinKey failed, tableName must not be empty, keyName: {}", keyName);
            return null;
        }

        if (keyName == null) {
            LOGGER.error("joinKey failed, keyName must not be null, tableName: {}", tableName);
            return null;
        }

        if (keyName.isEmpty()) {
            LOGGER.error("joinKey failed, keyName must not be empty, tableName: {}", tableName);
            return null;
        }

        return tableName + SEPARATOR + keyName;
    }

    /**
     * Json to Map
     *
     * @param json Json String
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> fromJson(String json) {
        if (json == null) {
            LOGGER.error("fromJson failed, json must not be null");
            return null;
        }

        if (json.isEmpty()) {
            LOGGER.error("fromJson failed, json must not be empty");
            return null;
        }

        return JsonParser.fromJson(json);
    }

    /**
     * Map to Json
     *
     * @param values [ Column Name : Column Value ]
     * @return Json String
     */
    public String toJson(Map<String, Value> values) {
        if (values != null) {
            return JsonParser.toJson(values);
        } else {
            LOGGER.error("toJson failed, values must not be null");
            return null;
        }
    }

    @NotNull
    public StringCache getStringCache() {
        return stringCache;
    }

}
