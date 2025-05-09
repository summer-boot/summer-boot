package io.github.summer.boot.xrepository.logger;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.sql.Order;
import io.github.summer.boot.sql.Page;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
public class LogRepositoryImpl implements LogRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRepositoryImpl.class);

    @Override
    public void selectList(String tableName, List<BaseFilter> filters, List<Order> orders, Page page, Value shardingValue, List<Map<String, Value>> result) {
        LOGGER.info("selectList trace, tableName: {}, filters: {}, orders: {}, page: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(orders), escapeJava(page), escapeJava(shardingValue), escapeJava(result));
    }

    @Override
    public void selectCount(String tableName, List<BaseFilter> filters, Value shardingValue, long result) {
        LOGGER.info("selectCount trace, tableName: {}, filters: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(shardingValue), result);
    }

    @Override
    public void selectOne(String tableName, Value keyValue, Map<String, Value> result, boolean useCache) {
        LOGGER.info("selectOne trace, tableName: {}, keyValue: {}, result: {}, useCache: {}",
                tableName, escapeJava(keyValue), escapeJava(result), useCache);
    }

    @Override
    public void selectOne(String tableName, List<BaseFilter> filters, List<Order> orders, Value shardingValue, Map<String, Value> result) {
        LOGGER.info("selectOne trace, tableName: {}, filters: {}, orders: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(orders), escapeJava(shardingValue), escapeJava(result));
    }

    @Override
    public void checkExist(String tableName, List<BaseFilter> filters, Value shardingValue, boolean result) {
        LOGGER.info("checkExist trace, tableName: {}, filters: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(shardingValue), result);
    }

    @Override
    public void insert(String tableName, Map<String, Value> values, int result) {
        LOGGER.info("insert trace, tableName: {}, values: {}, result: {}",
                tableName, escapeJava(values), result);
    }

    @Override
    public void batchInsert(String tableName, List<Map<String, Value>> list, Value shardingValue, int result) {
        LOGGER.info("batchInsert trace, tableName: {}, list: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(list), escapeJava(shardingValue), result);
    }

    @Override
    public void update(String tableName, Value keyValue, List<String> sets, Map<String, Value> setValues, int result) {
        LOGGER.info("update trace, tableName: {}, keyValue: {}, sets: {}, setValues: {}, result: {}",
                tableName, escapeJava(keyValue), escapeJava(sets), escapeJava(setValues), result);
    }

    @Override
    public void update(String tableName, List<String> sets, Map<String, Value> setValues, List<BaseFilter> filters, Value shardingValue, int result) {
        LOGGER.info("update trace, tableName: {}, sets: {}, setValues: {}, filters: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(sets), escapeJava(setValues), escapeJava(filters), escapeJava(shardingValue), result);
    }

    @Override
    public void batchUpdate(String tableName, List<String> sets, List<String> setNames, List<Map<String, Value>> list, Value shardingValue, int[] result) {
        LOGGER.info("batchUpdate trace, tableName: {}, sets: {}, setNames: {}, list: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(sets), escapeJava(setNames), escapeJava(list), escapeJava(shardingValue), escapeJava(result));
    }

    @Override
    public void delete(String tableName, Value keyValue, int result) {
        LOGGER.info("delete trace, tableName: {}, keyValue: {}, result: {}",
                tableName, escapeJava(keyValue), result);
    }

    @Override
    public void delete(String tableName, List<BaseFilter> filters, Value shardingValue, int result) {
        LOGGER.info("delete trace, tableName: {}, filters: {}, shardingValue: {}, result: {}",
                tableName, escapeJava(filters), escapeJava(shardingValue), result);
    }

    /**
     * org.apache.commons.text.StringEscapeUtils.escapeJava
     *
     * @param src the object
     * @return the safe object
     */
    public Object escapeJava(Object src) {
        return JsonParser.toJson(src);
    }

}
