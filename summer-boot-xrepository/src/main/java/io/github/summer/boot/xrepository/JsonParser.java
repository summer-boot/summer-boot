package io.github.summer.boot.xrepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.summer.boot.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 解析Json
 * 时间格式和时区
 * properties
 * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
 * spring.jackson.time-zone=GMT+8
 * annotation
 * &#064;JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
 *
 * @author changebooks@qq.com
 */
public final class JsonParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);

    /**
     * Parser
     */
    private static final ObjectMapper PARSER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Type Reference Of Map
     */
    private static final TypeReference<Map<String, Value>> TYPE_REFERENCE = new TypeReference<>() {
    };

    private JsonParser() {
    }

    /**
     * Convert Object to Json String
     *
     * @param src the object
     * @return a json string
     */
    public static String toJson(Object src) {
        try {
            return PARSER.writeValueAsString(src);
        } catch (Throwable ex) {
            LOGGER.error("toJson failed, writeValueAsString failed, throwable: ", ex);
            return null;
        }
    }

    /**
     * Convert Json String to Map
     *
     * @param json the json string
     * @return [ Column Name : Column Value ]
     */
    public static Map<String, Value> fromJson(String json) {
        try {
            return PARSER.readValue(json, TYPE_REFERENCE);
        } catch (Throwable ex) {
            LOGGER.error("fromJson failed, readValue failed, json: {}, throwable: ", json, ex);
            return null;
        }
    }

}
