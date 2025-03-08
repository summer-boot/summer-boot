package io.github.summer.boot.xdatabase;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 写日志
 *
 * @author changebooks@qq.com
 */
public final class LogWriterDefault {

    private LogWriterDefault() {
    }

    /**
     * Database
     */
    public static class Database implements LogWriter.Database {

        private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

        @Override
        public void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result) {
            LOGGER.info("selectList trace, tableName: {}, filters: {}, orders: {}, page: {}, result: {}",
                    tableName, escapeJava(filters), escapeJava(orders), escapeJava(page), escapeJava(result));
        }

        @Override
        public void selectCount(String tableName, List<BaseFilter> filters, long result) {
            LOGGER.info("selectCount trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        @Override
        public void selectOne(String tableName, Value idValue, Map<String, Value> result) {
            LOGGER.info("selectOne trace, tableName: {}, idValue: {}, result: {}",
                    tableName, escapeJava(idValue), escapeJava(result));
        }

        @Override
        public void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result) {
            LOGGER.info("selectOne trace, tableName: {}, filters: {}, orders: {}, result: {}",
                    tableName, escapeJava(filters), escapeJava(orders), escapeJava(result));
        }

        @Override
        public void checkExist(String tableName, List<BaseFilter> filters, boolean result) {
            LOGGER.info("checkExist trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        @Override
        public void insert(String tableName, Map<String, Value> values, int result) {
            LOGGER.info("insert trace, tableName: {}, values: {}, result: {}",
                    tableName, escapeJava(values), result);
        }

        @Override
        public void batchInsert(String tableName, List<Map<String, Value>> list, int result) {
            LOGGER.info("batchInsert trace, tableName: {}, list: {}, result: {}",
                    tableName, escapeJava(list), result);
        }

        @Override
        public void update(String tableName, Value idValue, List<String> sets, Map<String, Value> setValues, int result) {
            LOGGER.info("update trace, tableName: {}, idValue: {}, sets: {}, setValues: {}, result: {}",
                    tableName, escapeJava(idValue), escapeJava(sets), escapeJava(setValues), result);
        }

        @Override
        public void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, int result) {
            LOGGER.info("update trace, tableName: {}, sets: {}, setValues: {}, filters: {}, result: {}",
                    tableName, escapeJava(sets), escapeJava(setValues), escapeJava(filters), result);
        }

        @Override
        public void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, int[] result) {
            LOGGER.info("batchUpdate trace, tableName: {}, sets: {}, setNames: {}, list: {}, result: {}",
                    tableName, escapeJava(sets), escapeJava(setNames), escapeJava(list), escapeJava(result));
        }

        @Override
        public void delete(String tableName, Value idValue, int result) {
            LOGGER.info("delete trace, tableName: {}, idValue: {}, result: {}",
                    tableName, escapeJava(idValue), result);
        }

        @Override
        public void delete(String tableName, List<BaseFilter> filters, int result) {
            LOGGER.info("delete trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        /**
         * org.apache.commons.text.StringEscapeUtils.escapeJava
         *
         * @param src the object
         * @return the safe object
         */
        public Object escapeJava(Object src) {
            return src;
        }

    }

    /**
     * Template
     */
    public static class Template implements LogWriter.Template {

        private static final Logger LOGGER = LoggerFactory.getLogger(Template.class);

        @Override
        public void selectList(Schema schema, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result) {
            LOGGER.info("selectList trace, schema: {}, filters: {}, orders: {}, page: {}, result: {}",
                    escapeJava(schema), escapeJava(filters), escapeJava(orders), escapeJava(page), escapeJava(result));
        }

        @Override
        public void selectCount(String tableName, List<BaseFilter> filters, Long result) {
            LOGGER.info("selectCount trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        @Override
        public void selectOne(Schema schema, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result) {
            LOGGER.info("selectOne trace, schema: {}, filters: {}, orders: {}, result: {}",
                    escapeJava(schema), escapeJava(filters), escapeJava(orders), escapeJava(result));
        }

        @Override
        public void checkExist(String tableName, List<BaseFilter> filters, Integer result) {
            LOGGER.info("checkExist trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        @Override
        public void insert(Schema schema, Map<String, Value> values, int result) {
            LOGGER.info("insert trace, schema: {}, values: {}, result: {}",
                    escapeJava(schema), escapeJava(values), result);
        }

        @Override
        public void batchInsert(Schema schema, List<Map<String, Value>> list, int result) {
            LOGGER.info("batchInsert trace, schema: {}, list: {}, result: {}",
                    escapeJava(schema), escapeJava(list), result);
        }

        @Override
        public void update(String tableName, List<String> sets, List<String> setNames, Map<String, Value> setValues, List<BaseFilter> filters, int result) {
            LOGGER.info("update trace, tableName: {}, sets: {}, setNames: {}, setValues: {}, filters: {}, result: {}",
                    tableName, escapeJava(sets), escapeJava(setNames), escapeJava(setValues), escapeJava(filters), result);
        }

        @Override
        public void batchUpdate(Schema schema, List<String> sets, List<String> setNames, List<Map<String, Value>> list, int[] result) {
            LOGGER.info("batchUpdate trace, schema: {}, sets: {}, setNames: {}, list: {}, result: {}",
                    escapeJava(schema), escapeJava(sets), escapeJava(setNames), escapeJava(list), escapeJava(result));
        }

        @Override
        public void delete(String tableName, List<BaseFilter> filters, int result) {
            LOGGER.info("delete trace, tableName: {}, filters: {}, result: {}",
                    tableName, escapeJava(filters), result);
        }

        /**
         * org.apache.commons.text.StringEscapeUtils.escapeJava
         *
         * @param src the object
         * @return the safe object
         */
        public Object escapeJava(Object src) {
            return src;
        }

    }

    /**
     * Executor
     */
    public static class Executor implements LogWriter.Executor {

        private static final Logger LOGGER = LoggerFactory.getLogger(Executor.class);

        @Override
        public void selectList(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, List<Map<String, Value>> result) {
            LOGGER.info("selectList trace, sqlParameter: {}, columnNames: {}, valueTypes: {}, result: {}",
                    escapeJava(sqlParameter), escapeJava(columnNames), escapeJava(valueTypes), escapeJava(result));
        }

        @Override
        public void selectOne(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, Map<String, Value> result) {
            LOGGER.info("selectOne trace, sqlParameter: {}, columnNames: {}, valueTypes: {}, result: {}",
                    escapeJava(sqlParameter), escapeJava(columnNames), escapeJava(valueTypes), escapeJava(result));
        }

        @Override
        public void getOne(SqlParameter sqlParameter, int valueType, Value result) {
            LOGGER.info("getOne trace, sqlParameter: {}, valueType: {}, result: {}",
                    escapeJava(sqlParameter), valueType, escapeJava(result));
        }

        @Override
        public void update(SqlParameter sqlParameter, int result) {
            LOGGER.info("update trace, sqlParameter: {}, result: {}",
                    escapeJava(sqlParameter), result);
        }

        @Override
        public void updateList(SqlParameter sqlParameter, int result) {
            LOGGER.info("updateList trace, sqlParameter: {}, result: {}",
                    escapeJava(sqlParameter), result);
        }

        @Override
        public void batchUpdate(SqlParameter sqlParameter, int[] result) {
            LOGGER.info("batchUpdate trace, sqlParameter: {}, result: {}",
                    escapeJava(sqlParameter), escapeJava(result));
        }

        /**
         * org.apache.commons.text.StringEscapeUtils.escapeJava
         *
         * @param src the object
         * @return the safe object
         */
        public Object escapeJava(Object src) {
            return src;
        }

    }

}
