package io.github.summer.boot.xdatabase.logger;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.schema.TableSchema;

import java.util.List;
import java.util.Map;

/**
 * Log Template
 *
 * @author changebooks@qq.com
 */
public interface LogTemplate {
    /**
     * SELECT LIST
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param filters     [ the {@link BaseFilter} instance ]
     * @param orders      [ the {@link Order} instance ]
     * @param page        the {@link Page} instance
     * @param result      [ [ Column Name : Column Value ] ]
     */
    void selectList(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result);

    /**
     * SELECT COUNT
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AGGREGATE
     */
    void selectCount(String tableName, List<BaseFilter> filters, Long result);

    /**
     * SELECT ONE
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param filters     [ the {@link BaseFilter} instance ]
     * @param orders      [ the {@link Order} instance ]
     * @param result      [ Column Name : Column Value ]
     */
    void selectOne(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result);

    /**
     * CHECK EXIST
     *
     * @param tableName FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    EXIST ? 1 : null
     */
    void checkExist(String tableName, List<BaseFilter> filters, Integer result);

    /**
     * INSERT
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param values      [ Column Name : Column Value ]
     * @param result      AFFECTED ROWS
     */
    void insert(TableSchema tableSchema, Map<String, Value> values, int result);

    /**
     * BATCH INSERT
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param list        [ [ Column Name : Column Value ] ]
     * @param result      AFFECTED ROWS
     */
    void batchInsert(TableSchema tableSchema, List<Map<String, Value>> list, int result);

    /**
     * UPDATE
     *
     * @param tableName UPDATE table
     * @param sets      [ column = column + 1 ]
     * @param setNames  [ Set Name ]
     * @param setValues [ Set Name : Set Value ]
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AFFECTED ROWS
     */
    void update(String tableName, List<String> sets, List<String> setNames, Map<String, Value> setValues, List<BaseFilter> filters, int result);

    /**
     * BATCH UPDATE
     *
     * @param tableSchema the {@link TableSchema} instance
     * @param sets        [ column = column + 1 ]
     * @param setNames    [ Set Name ]
     * @param list        [ [ Parameter Name : Parameter Value ] ]
     * @param keyName     Key Name, if null ? Primary Key
     * @param result      AFFECTED ROWS
     */
    void batchUpdate(TableSchema tableSchema, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, int[] result);

    /**
     * DELETE
     *
     * @param tableName DELETE FROM table
     * @param filters   [ the {@link BaseFilter} instance ]
     * @param result    AFFECTED ROWS
     */
    void delete(String tableName, List<BaseFilter> filters, int result);

}
