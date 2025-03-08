package io.github.summer.boot.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
     * Object Mapper Type Factory
     */
    private static final TypeFactory TYPE_FACTORY = PARSER.getTypeFactory();

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
     * Convert Json String to Object
     *
     * @param json     the json string
     * @param classOfT the class of T
     * @param <T>      the type of the desired object
     * @return an object of type T from the string
     */
    public static <T> T fromJson(String json, final Class<T> classOfT) {
        try {
            return PARSER.readValue(json, classOfT);
        } catch (Throwable ex) {
            LOGGER.error("fromJson failed, readValue failed, json: {}, throwable: ", json, ex);
            return null;
        }
    }

    /**
     * Convert Json String to Object
     *
     * @param json    the json string
     * @param typeOfT new TypeReference&lt;Collection&lt;Foo&gt;&gt;()
     * @param <T>     the type of the desired object
     * @return an object of type T from the string
     */
    public static <T> T fromJson(String json, final TypeReference<T> typeOfT) {
        try {
            return PARSER.readValue(json, typeOfT);
        } catch (Throwable ex) {
            LOGGER.error("fromJson failed, readValue failed, json: {}, throwable: ", json, ex);
            return null;
        }
    }

    /**
     * Convert Json String to Object List
     *
     * @param json     the json string
     * @param classOfT the class of T
     * @param <T>      the type of the desired object
     * @return object list of type T from the string
     */
    public static <T> List<T> fromList(String json, final Class<T> classOfT) {
        CollectionType listType = TYPE_FACTORY.constructCollectionType(List.class, classOfT);
        return fromList(json, listType);
    }

    /**
     * Convert Json String to Object List
     *
     * @param json     the json string
     * @param listType the type of list
     * @param <T>      the type of the desired object
     * @return object list of type T from the string
     */
    public static <T> List<T> fromList(String json, final CollectionType listType) {
        try {
            return PARSER.readValue(json, listType);
        } catch (Throwable ex) {
            LOGGER.error("fromList failed, readValue failed, json: {}, throwable: ", json, ex);
            return null;
        }
    }

}
