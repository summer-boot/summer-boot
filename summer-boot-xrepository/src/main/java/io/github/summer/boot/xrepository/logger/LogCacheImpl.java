package io.github.summer.boot.xrepository.logger;

import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author changebooks@qq.com
 */
public class LogCacheImpl implements LogCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogCacheImpl.class);

    @Override
    public void get(String tableName, Value keyValue, Map<String, Value> result) {
        LOGGER.info("get trace, tableName: {}, keyValue: {}, result: {}",
                tableName, escapeJava(keyValue), escapeJava(result));
    }

    @Override
    public void set(String tableName, Value keyValue, Map<String, Value> values, boolean result) {
        if (result) {
            LOGGER.info("set trace, tableName: {}, keyValue: {}, values: {}",
                    tableName, escapeJava(keyValue), escapeJava(values));
        } else {
            LOGGER.error("set failed, tableName: {}, keyValue: {}, values: {}",
                    tableName, escapeJava(keyValue), escapeJava(values));
        }
    }

    @Override
    public void delete(String tableName, Value keyValue, boolean result) {
        if (result) {
            LOGGER.info("delete trace, tableName: {}, keyValue: {}",
                    tableName, escapeJava(keyValue));
        } else {
            LOGGER.error("delete failed, tableName: {}, keyValue: {}",
                    tableName, escapeJava(keyValue));
        }
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
