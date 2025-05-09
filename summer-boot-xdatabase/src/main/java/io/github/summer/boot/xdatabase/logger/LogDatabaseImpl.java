package io.github.summer.boot.xdatabase.logger;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.Page;
import io.github.summer.boot.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
public class LogDatabaseImpl implements LogDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDatabaseImpl.class);

    @Override
    public void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Integer tableNum, List<Map<String, Value>> result) {
        LOGGER.info("selectList trace, tableName: {}, filters: {}, orders: {}, page: {}, tableNum: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(orders), escapeJava(page), tableNum, escapeJava(result));
    }

    @Override
    public void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Integer tableNum, Throwable ex) {
        LOGGER.error("selectList failed, tableName: {}, filters: {}, orders: {}, page: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(filters), escapeJava(orders), escapeJava(page), tableNum, ex);
    }

    @Override
    public void selectCount(String tableName, List<BaseFilter> filters, Integer tableNum, long result) {
        LOGGER.info("selectCount trace, tableName: {}, filters: {}, tableNum: {}, result: {}",
                tableName, escapeJava(filters), tableNum, result);
    }

    @Override
    public void selectCount(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        LOGGER.error("selectCount failed, tableName: {}, filters: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(filters), tableNum, ex);
    }

    @Override
    public void selectOne(String tableName, Value keyValue, String keyName, Integer tableNum, Map<String, Value> result) {
        LOGGER.info("selectOne trace, tableName: {}, keyValue: {}, keyName: {}, tableNum: {}, result: {}",
                tableName, escapeJava(keyValue), keyName, tableNum, escapeJava(result));
    }

    @Override
    public void selectOne(String tableName, Value keyValue, String keyName, Integer tableNum, Throwable ex) {
        LOGGER.error("selectOne failed, tableName: {}, keyValue: {}, keyName: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(keyValue), keyName, tableNum, ex);
    }

    @Override
    public void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Integer tableNum, Map<String, Value> result) {
        LOGGER.info("selectOne trace, tableName: {}, filters: {}, orders: {}, tableNum: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(orders), tableNum, escapeJava(result));
    }

    @Override
    public void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Integer tableNum, Throwable ex) {
        LOGGER.error("selectOne failed, tableName: {}, filters: {}, orders: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(filters), escapeJava(orders), tableNum, ex);
    }

    @Override
    public void checkExist(String tableName, List<BaseFilter> filters, Integer tableNum, boolean result) {
        LOGGER.info("checkExist trace, tableName: {}, filters: {}, tableNum: {}, result: {}",
                tableName, escapeJava(filters), tableNum, result);
    }

    @Override
    public void checkExist(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        LOGGER.error("checkExist failed, tableName: {}, filters: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(filters), tableNum, ex);
    }

    @Override
    public void insert(String tableName, Map<String, Value> values, Integer tableNum, int result) {
        LOGGER.info("insert trace, tableName: {}, values: {}, tableNum: {}, result: {}",
                tableName, escapeJava(values), tableNum, result);
    }

    @Override
    public void insert(String tableName, Map<String, Value> values, Integer tableNum, Throwable ex) {
        LOGGER.error("insert failed, tableName: {}, values: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(values), tableNum, ex);
    }

    @Override
    public void batchInsert(String tableName, List<Map<String, Value>> list, Integer tableNum, int result) {
        LOGGER.info("batchInsert trace, tableName: {}, list: {}, tableNum: {}, result: {}",
                tableName, escapeJava(list), tableNum, result);
    }

    @Override
    public void batchInsert(String tableName, List<Map<String, Value>> list, Integer tableNum, Throwable ex) {
        LOGGER.error("batchInsert failed, tableName: {}, list: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(list), tableNum, ex);
    }

    @Override
    public void update(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, Integer tableNum, int result) {
        LOGGER.info("update trace, tableName: {}, keyValue: {}, sets: {}, setValues: {}, keyName: {}, tableNum: {}, result: {}",
                tableName, escapeJava(keyValue), escapeJava(sets), escapeJava(setValues), keyName, tableNum, result);
    }

    @Override
    public void update(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, String keyName, Integer tableNum, Throwable ex) {
        LOGGER.error("update failed, tableName: {}, keyValue: {}, sets: {}, setValues: {}, keyName: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(keyValue), escapeJava(sets), escapeJava(setValues), keyName, tableNum, ex);
    }

    @Override
    public void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Integer tableNum, int result) {
        LOGGER.info("update trace, tableName: {}, sets: {}, setValues: {}, filters: {}, tableNum: {}, result: {}",
                tableName, escapeJava(sets), escapeJava(setValues), escapeJava(filters), tableNum, result);
    }

    @Override
    public void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        LOGGER.error("update failed, tableName: {}, sets: {}, setValues: {}, filters: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(sets), escapeJava(setValues), escapeJava(filters), tableNum, ex);
    }

    @Override
    public void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, Integer tableNum, int[] result) {
        LOGGER.info("batchUpdate trace, tableName: {}, sets: {}, setNames: {}, list: {}, keyName: {}, tableNum: {}, result: {}",
                tableName, escapeJava(sets), escapeJava(setNames), escapeJava(list), keyName, tableNum, escapeJava(result));
    }

    @Override
    public void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, String keyName, Integer tableNum, Throwable ex) {
        LOGGER.error("batchUpdate failed, tableName: {}, sets: {}, setNames: {}, list: {}, keyName: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(sets), escapeJava(setNames), escapeJava(list), keyName, tableNum, ex);
    }

    @Override
    public void delete(String tableName, Value keyValue, String keyName, Integer tableNum, int result) {
        LOGGER.info("delete trace, tableName: {}, keyValue: {}, keyName: {}, tableNum: {}, result: {}",
                tableName, escapeJava(keyValue), keyName, tableNum, result);
    }

    @Override
    public void delete(String tableName, Value keyValue, String keyName, Integer tableNum, Throwable ex) {
        LOGGER.error("delete failed, tableName: {}, keyValue: {}, keyName: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(keyValue), keyName, tableNum, ex);
    }

    @Override
    public void delete(String tableName, List<BaseFilter> filters, Integer tableNum, int result) {
        LOGGER.info("delete trace, tableName: {}, filters: {}, tableNum: {}, result: {}",
                tableName, escapeJava(filters), tableNum, result);
    }

    @Override
    public void delete(String tableName, List<BaseFilter> filters, Integer tableNum, Throwable ex) {
        LOGGER.error("delete failed, tableName: {}, filters: {}, tableNum: {}, throwable: ",
                tableName, escapeJava(filters), tableNum, ex);
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
