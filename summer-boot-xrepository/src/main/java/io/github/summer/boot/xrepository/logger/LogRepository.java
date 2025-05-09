package io.github.summer.boot.xrepository.logger;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.Page;
import io.github.summer.boot.value.Value;

import java.util.List;
import java.util.Map;

/**
 * Log Repository
 *
 * @author changebooks@qq.com
 */
public interface LogRepository {
    /**
     * SELECT LIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param page          the {@link Page} instance
     * @param shardingValue Sharding Value
     * @param result        [ [ Column Name : Column Value ] ]
     */
    void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Value shardingValue, List<Map<String, Value>> result);

    /**
     * SELECT COUNT
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @param result        AGGREGATE
     */
    void selectCount(String tableName, List<BaseFilter> filters, Value shardingValue, long result);

    /**
     * SELECT ONE
     *
     * @param tableName FROM table
     * @param keyValue  Key Value
     * @param result    [ Column Name : Column Value ]
     * @param useCache  Use Cache
     */
    void selectOne(String tableName, Value keyValue, Map<String, Value> result, boolean useCache);

    /**
     * SELECT ONE
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param orders        [ the {@link Order} instance ]
     * @param shardingValue Sharding Value
     * @param result        [ Column Name : Column Value ]
     */
    void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Value shardingValue, Map<String, Value> result);

    /**
     * CHECK EXIST
     *
     * @param tableName     FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @param result        EXIST ? true : false
     */
    void checkExist(String tableName, List<BaseFilter> filters, Value shardingValue, boolean result);

    /**
     * INSERT
     *
     * @param tableName INSERT INTO table
     * @param values    [ Column Name : Column Value ]
     * @param result    AFFECTED ROWS
     */
    void insert(String tableName, Map<String, Value> values, int result);

    /**
     * BATCH INSERT
     *
     * @param tableName     INSERT INTO table
     * @param list          [ [ Column Name : Column Value ] ]
     * @param shardingValue Sharding Value
     * @param result        AFFECTED ROWS
     */
    void batchInsert(String tableName, List<Map<String, Value>> list, Value shardingValue, int result);

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param keyValue  Key Value
     * @param sets      [ column = column + 1 ]
     * @param setValues [ Set Name : Set Value ]
     * @param result    AFFECTED ROWS
     */
    void update(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, int result);

    /**
     * UPDATE
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setValues     [ Set Name : Set Value ]
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @param result        AFFECTED ROWS
     */
    void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Value shardingValue, int result);

    /**
     * BATCH UPDATE
     *
     * @param tableName     UPDATE table
     * @param sets          [ column = column + 1 ]
     * @param setNames      [ Set Name ]
     * @param list          [ [ Parameter Name : Parameter Value ] ]
     * @param shardingValue Sharding Value
     * @param result        AFFECTED ROWS
     */
    void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, Value shardingValue, int[] result);

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param keyValue  Key Value
     * @param result    AFFECTED ROWS
     */
    void delete(String tableName, Value keyValue, int result);

    /**
     * DELETE
     *
     * @param tableName     DELETE FROM table
     * @param filters       [ the {@link BaseFilter} instance ]
     * @param shardingValue Sharding Value
     * @param result        AFFECTED ROWS
     */
    void delete(String tableName, List<BaseFilter> filters, Value shardingValue, int result);

}
