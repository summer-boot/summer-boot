package io.github.summer.boot.util;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.summer.boot.base.Check;
import io.github.summer.boot.base.JsonParser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 解析Json和Map
 *
 * @author changebooks@qq.com
 */
public final class JsonMap {
    /**
     * 对象
     */
    private static final AbstractParser<Object> OBJECT_PARSER = new AbstractParser<>() {
    };

    /**
     * 字符串
     */
    private static final AbstractParser<String> STRING_PARSER = new AbstractParser<>() {
    };

    /**
     * 整数
     */
    private static final AbstractParser<Integer> INTEGER_PARSER = new AbstractParser<>() {
    };

    /**
     * 长整数
     */
    private static final AbstractParser<Long> LONG_PARSER = new AbstractParser<>() {
    };

    /**
     * 小数
     */
    private static final AbstractParser<BigDecimal> BIG_DECIMAL_PARSER = new AbstractParser<>() {
    };

    /**
     * 日期时间
     */
    private static final AbstractParser<Date> DATE_PARSER = new AbstractParser<>() {
    };

    private JsonMap() {
    }

    /**
     * 对象
     *
     * @param src the map
     * @return a json string
     */
    public static String writeObject(Map<String, Object> src) {
        return OBJECT_PARSER.toJson(src);
    }

    /**
     * 对象
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, Object> readObject(String json) {
        return OBJECT_PARSER.fromJson(json);
    }

    /**
     * 字符串
     *
     * @param src the map
     * @return a json string
     */
    public static String writeString(Map<String, String> src) {
        return STRING_PARSER.toJson(src);
    }

    /**
     * 字符串
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, String> readString(String json) {
        return STRING_PARSER.fromJson(json);
    }

    /**
     * 整数
     *
     * @param src the map
     * @return a json string
     */
    public static String writeInteger(Map<String, Integer> src) {
        return INTEGER_PARSER.toJson(src);
    }

    /**
     * 整数
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, Integer> readInteger(String json) {
        return INTEGER_PARSER.fromJson(json);
    }

    /**
     * 长整数
     *
     * @param src the map
     * @return a json string
     */
    public static String writeLong(Map<String, Long> src) {
        return LONG_PARSER.toJson(src);
    }

    /**
     * 长整数
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, Long> readLong(String json) {
        return LONG_PARSER.fromJson(json);
    }

    /**
     * 小数
     *
     * @param src the map
     * @return a json string
     */
    public static String writeBigDecimal(Map<String, BigDecimal> src) {
        return BIG_DECIMAL_PARSER.toJson(src);
    }

    /**
     * 小数
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, BigDecimal> readBigDecimal(String json) {
        return BIG_DECIMAL_PARSER.fromJson(json);
    }

    /**
     * 日期时间
     *
     * @param src the map
     * @return a json string
     */
    public static String writeDate(Map<String, Date> src) {
        return DATE_PARSER.toJson(src);
    }

    /**
     * 日期时间
     *
     * @param json the json string
     * @return a map
     */
    public static Map<String, Date> readDate(String json) {
        return DATE_PARSER.fromJson(json);
    }

    /**
     * 执行解析
     *
     * @param <T> the type of the desired object
     */
    private abstract static class AbstractParser<T> {
        /**
         * Type Reference Of Map
         */
        private final TypeReference<Map<String, T>> TYPE_REFERENCE = new TypeReference<>() {
        };

        /**
         * Convert Map to Json String
         *
         * @param src the Map
         * @return a json string
         */
        public String toJson(Map<String, T> src) {
            if (src != null) {
                return JsonParser.toJson(src);
            } else {
                return null;
            }
        }

        /**
         * Convert Json String to Map
         *
         * @param json the json string
         * @return a map of type T from the string
         */
        public Map<String, T> fromJson(String json) {
            if (Check.nonEmpty(json)) {
                return JsonParser.fromJson(json, TYPE_REFERENCE);
            } else {
                return null;
            }
        }

    }

}
