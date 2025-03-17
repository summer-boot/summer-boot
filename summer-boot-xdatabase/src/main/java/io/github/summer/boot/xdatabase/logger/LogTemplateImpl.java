package io.github.summer.boot.xdatabase.logger;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Order;
import io.github.summer.boot.filter.Page;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.schema.TableSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
public class LogTemplateImpl implements LogTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTemplateImpl.class);

    @Override
    public void selectList(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Page page, List<Map<String, Value>> result) {
        LOGGER.info("selectList trace, tableSchema: {}, filters: {}, orders: {}, page: {}, result: {}",
                escapeJava(tableSchema), escapeJava(filters), escapeJava(orders), escapeJava(page), escapeJava(result));
    }

    @Override
    public void selectCount(String tableName, List<BaseFilter> filters, Long result) {
        LOGGER.info("selectCount trace, tableName: {}, filters: {}, result: {}",
                tableName, escapeJava(filters), result);
    }

    @Override
    public void selectOne(TableSchema tableSchema, List<BaseFilter> filters, List<Order> orders, Map<String, Value> result) {
        LOGGER.info("selectOne trace, tableSchema: {}, filters: {}, orders: {}, result: {}",
                escapeJava(tableSchema), escapeJava(filters), escapeJava(orders), escapeJava(result));
    }

    @Override
    public void checkExist(String tableName, List<BaseFilter> filters, Integer result) {
        LOGGER.info("checkExist trace, tableName: {}, filters: {}, result: {}",
                tableName, escapeJava(filters), result);
    }

    @Override
    public void insert(TableSchema tableSchema, Map<String, Value> values, int result) {
        LOGGER.info("insert trace, tableSchema: {}, values: {}, result: {}",
                escapeJava(tableSchema), escapeJava(values), result);
    }

    @Override
    public void batchInsert(TableSchema tableSchema, List<Map<String, Value>> list, int result) {
        LOGGER.info("batchInsert trace, tableSchema: {}, list: {}, result: {}",
                escapeJava(tableSchema), escapeJava(list), result);
    }

    @Override
    public void update(String tableName, List<String> sets, List<String> setNames, Map<String, Value> setValues, List<BaseFilter> filters, int result) {
        LOGGER.info("update trace, tableName: {}, sets: {}, setNames: {}, setValues: {}, filters: {}, result: {}",
                tableName, escapeJava(sets), escapeJava(setNames), escapeJava(setValues), escapeJava(filters), result);
    }

    @Override
    public void batchUpdate(TableSchema tableSchema, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, int[] result) {
        LOGGER.info("batchUpdate trace, tableSchema: {}, sets: {}, setNames: {}, list: {}, keyName: {}, result: {}",
                escapeJava(tableSchema), escapeJava(sets), escapeJava(setNames), escapeJava(list), keyName, escapeJava(result));
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
