package io.github.summer.boot.xdatabase.logger;

import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author changebooks@qq.com
 */
public class LogExecutorImpl implements LogExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogExecutorImpl.class);

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
