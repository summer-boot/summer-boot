package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;

import java.util.List;
import java.util.Map;

/**
 * 写日志
 *
 * @author changebooks@qq.com
 */
public final class LogWriter {
    /**
     * the {@link Database} instance
     */
    private static Database database;

    /**
     * the {@link Template} instance
     */
    private static Template template;

    /**
     * the {@link Executor} instance
     */
    private static Executor executor;

    private LogWriter() {
    }

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        LogWriter.database = database;
    }

    public static Template getTemplate() {
        return template;
    }

    public static void setTemplate(Template template) {
        LogWriter.template = template;
    }

    public static Executor getExecutor() {
        return executor;
    }

    public static void setExecutor(Executor executor) {
        LogWriter.executor = executor;
    }

    /**
     * Database
     */
    public interface Database {
        /**
         * SELECT LIST
         *
         * @param tableName FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param orders    [ the {@link Order} instance ]
         * @param page      the {@link Page} instance
         * @param result    [ [ Column Name : Column Value ] ]
         */
        void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result);

        /**
         * SELECT COUNT
         *
         * @param tableName FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param result    AGGREGATE
         */
        void selectCount(String tableName, List<BaseFilter> filters, long result);

        /**
         * SELECT ONE
         *
         * @param tableName FROM table
         * @param idValue   Primary Key Value
         * @param result    [ Column Name : Column Value ]
         */
        void selectOne(String tableName, Value idValue, Map<String, Value> result);

        /**
         * SELECT ONE
         *
         * @param tableName FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param orders    [ the {@link Order} instance ]
         * @param result    [ Column Name : Column Value ]
         */
        void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result);

        /**
         * CHECK EXIST
         *
         * @param tableName FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param result    EXIST ? true : false
         */
        void checkExist(String tableName, List<BaseFilter> filters, boolean result);

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
         * @param tableName INSERT INTO table
         * @param list      [ [ Column Name : Column Value ] ]
         * @param result    AFFECTED ROWS
         */
        void batchInsert(String tableName, List<Map<String, Value>> list, int result);

        /**
         * UPDATE
         *
         * @param tableName UPDATE table
         * @param idValue   Primary Key Value
         * @param sets      [ column = column + 1 ]
         * @param setValues [ Set Name : Set Value ]
         * @param result    AFFECTED ROWS
         */
        void update(String tableName, Value idValue, List<String> sets, Map<String, Value> setValues, int result);

        /**
         * UPDATE
         *
         * @param tableName UPDATE table
         * @param sets      [ column = column + 1 ]
         * @param setValues [ Set Name : Set Value ]
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param result    AFFECTED ROWS
         */
        void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, int result);

        /**
         * BATCH UPDATE
         *
         * @param tableName UPDATE table
         * @param sets      [ column = column + 1 ]
         * @param setNames  [ Set Name ]
         * @param list      [ [ Parameter Name : Parameter Value ] ]
         * @param result    AFFECTED ROWS
         */
        void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, int[] result);

        /**
         * DELETE
         *
         * @param tableName DELETE FROM table
         * @param idValue   Primary Key Value
         * @param result    AFFECTED ROWS
         */
        void delete(String tableName, Value idValue, int result);

        /**
         * DELETE
         *
         * @param tableName DELETE FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param result    AFFECTED ROWS
         */
        void delete(String tableName, List<BaseFilter> filters, int result);

    }

    /**
     * Template
     */
    public interface Template {
        /**
         * SELECT LIST
         *
         * @param schema  the {@link Schema} instance
         * @param filters [ the {@link BaseFilter} instance ]
         * @param orders  [ the {@link Order} instance ]
         * @param page    the {@link Page} instance
         * @param result  [ [ Column Name : Column Value ] ]
         */
        void selectList(Schema schema, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result);

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
         * @param schema  the {@link Schema} instance
         * @param filters [ the {@link BaseFilter} instance ]
         * @param orders  [ the {@link Order} instance ]
         * @param result  [ Column Name : Column Value ]
         */
        void selectOne(Schema schema, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result);

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
         * @param schema the {@link Schema} instance
         * @param values [ Column Name : Column Value ]
         * @param result AFFECTED ROWS
         */
        void insert(Schema schema, Map<String, Value> values, int result);

        /**
         * BATCH INSERT
         *
         * @param schema the {@link Schema} instance
         * @param list   [ [ Column Name : Column Value ] ]
         * @param result AFFECTED ROWS
         */
        void batchInsert(Schema schema, List<Map<String, Value>> list, int result);

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
         * @param schema   the {@link Schema} instance
         * @param sets     [ column = column + 1 ]
         * @param setNames [ Set Name ]
         * @param list     [ [ Parameter Name : Parameter Value ] ]
         * @param result   AFFECTED ROWS
         */
        void batchUpdate(Schema schema, List<String> sets, List<String> setNames, List<Map<String, Value>> list, int[] result);

        /**
         * DELETE
         *
         * @param tableName DELETE FROM table
         * @param filters   [ the {@link BaseFilter} instance ]
         * @param result    AFFECTED ROWS
         */
        void delete(String tableName, List<BaseFilter> filters, int result);

    }

    /**
     * Executor
     */
    public interface Executor {
        /**
         * SELECT LIST
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param columnNames  [ Column Name ]
         * @param valueTypes   [ Column Name : Value Type ]
         * @param result       [ [ Column Name : Column Value ] ]
         */
        void selectList(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, List<Map<String, Value>> result);

        /**
         * SELECT ONE
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param columnNames  [ Column Name ]
         * @param valueTypes   [ Column Name : Value Type ]
         * @param result       [ Column Name : Column Value ]
         */
        void selectOne(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, Map<String, Value> result);

        /**
         * GET ONE
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param valueType    Value Type
         * @param result       Column Value
         */
        void getOne(SqlParameter sqlParameter, int valueType, Value result);

        /**
         * UPDATE
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param result       Affected Rows
         */
        void update(SqlParameter sqlParameter, int result);

        /**
         * UPDATE LIST
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param result       Affected Rows
         */
        void updateList(SqlParameter sqlParameter, int result);

        /**
         * BATCH UPDATE
         *
         * @param sqlParameter the {@link SqlParameter} instance
         * @param result       Affected Rows
         */
        void batchUpdate(SqlParameter sqlParameter, int[] result);

    }

}
