package io.github.summer.boot.xrepository;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.cache.CacheRegistry;
import io.github.summer.boot.xrepository.cache.ValueCache;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Repository Cache
 *
 * @author changebooks@qq.com
 */
public class RepositoryCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCache.class);

    /**
     * the {@link Repository} instance
     */
    private final Repository repository;

    /**
     * the {@link ValueCache} instance
     */
    private final ValueCache valueCache;

    public RepositoryCache(Repository repository, ValueCache valueCache) {
        Preconditions.requireNonNull(repository, "repository must not be null");
        Preconditions.requireNonNull(valueCache, "valueCache must not be null");

        this.repository = repository;
        this.valueCache = valueCache;
    }

    /**
     * SELECT LIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param page          the {@link Page} instance
     * @param shardingValue Sharding Value
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull String tableName,
                                               List<BaseFilter> filters, List<Order> orders, Page page,
                                               @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.selectList(tableName, filters, orders, page, shardingValue);
    }

    /**
     * SELECT COUNT
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AGGREGATE
     */
    public long selectCount(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.selectCount(tableName, filters, shardingValue);
    }

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName, @NotNull Value keyValue) {
        Repository repository = getRepository();

        Map<String, Value> result = getCache(tableName, keyValue);
        if (result != null) {
            return result;
        } else {
            return repository.selectOne(tableName, keyValue);
        }
    }

    /**
     * SELECT ONE
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param shardingValue Sharding Value
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull String tableName,
                                        List<BaseFilter> filters, List<Order> orders,
                                        @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.selectOne(tableName, filters, orders, shardingValue);
    }

    /**
     * CHECK EXIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return EXIST ? true : false
     */
    public boolean checkExist(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.checkExist(tableName, filters, shardingValue);
    }

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @return AFFECTED ROWS
     */
    public int insert(@NotNull String tableName, @NotNull Map<String, Value> values) {
        Repository repository = getRepository();
        return repository.insert(tableName, values);
    }

    /**
     * BATCH INSERT
     *
     * @param tableName     INSERT INTO table
     * @param list          [ [ Column Name : Column Value ] ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int batchInsert(@NotNull String tableName, @NotNull List<Map<String, Value>> list, @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.batchInsert(tableName, list, shardingValue);
    }

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      @NotNull Value keyValue,
                      List<String> sets, Map<String, Value> setValues) {
        Repository repository = getRepository();
        int affectedRows = repository.update(tableName, keyValue, sets, setValues);
        if (affectedRows > 0) {
            deleteCache(tableName, keyValue);
        }

        return affectedRows;
    }

    /**
     * UPDATE
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setValues     [ Set Name : Set Value ]
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int update(@NotNull String tableName,
                      List<String> sets, Map<String, Value> setValues,
                      List<BaseFilter> filters,
                      @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.update(tableName, sets, setValues, filters, shardingValue);
    }

    /**
     * BATCH UPDATE, No Transactional
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setNames      [ Set Name ]
     * @param list          [ [ Parameter Name : Parameter Value ] ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull String tableName,
                             List<String> sets, List<String> setNames,
                             List<Map<String, Value>> list,
                             @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.batchUpdate(tableName, sets, setNames, list, shardingValue);
    }

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, @NotNull Value keyValue) {
        Repository repository = getRepository();
        int affectedRows = repository.delete(tableName, keyValue);
        if (affectedRows > 0) {
            deleteCache(tableName, keyValue);
        }

        return affectedRows;
    }

    /**
     * DELETE
     *
     * @param tableName     DELETE FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @return AFFECTED ROWS
     */
    public int delete(@NotNull String tableName, List<BaseFilter> filters, @Nullable Value shardingValue) {
        Repository repository = getRepository();
        return repository.delete(tableName, filters, shardingValue);
    }

    /**
     * 读取缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> getCache(@NotNull String tableName, Value keyValue) {
        Integer cacheTime = getCacheTime(tableName);
        if (cacheTime == null) {
            return null;
        } else {
            ValueCache valueCache = getValueCache();
            return valueCache.get(tableName, keyValue);
        }
    }

    /**
     * 设置缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     * @param values    [ Column Name : Column Value ]
     */
    public void setCache(@NotNull String tableName, Value keyValue, Map<String, Value> values) {
        Integer cacheTime = getCacheTime(tableName);
        if (cacheTime == null) {
            return;
        }

        ValueCache valueCache = getValueCache();
        boolean result = valueCache.set(tableName, keyValue, values, cacheTime);
        if (!result) {
            LOGGER.error("setCache failed, tableName: {}, keyValue: {}, values: {}, cacheTime: {}",
                    tableName, keyValue, JsonParser.toJson(values), cacheTime);
        }
    }

    /**
     * 删除缓存
     *
     * @param tableName 表名
     * @param keyValue  键值
     */
    public void deleteCache(@NotNull String tableName, Value keyValue) {
        Integer cacheTime = getCacheTime(tableName);
        if (cacheTime == null) {
            return;
        }

        ValueCache valueCache = getValueCache();
        boolean result = valueCache.delete(tableName, keyValue);
        if (!result) {
            LOGGER.error("deleteCache failed, tableName: {}, keyValue: {}", tableName, keyValue);
        }
    }

    /**
     * Get Cache Time
     *
     * @param tableName Table Name
     * @return Cache Time
     */
    @Nullable
    public Integer getCacheTime(@NotNull String tableName) {
        return CacheRegistry.get(tableName);
    }

    @NotNull
    public Repository getRepository() {
        return repository;
    }

    @NotNull
    public ValueCache getValueCache() {
        return valueCache;
    }

}
