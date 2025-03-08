package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Build WHERE SQL
 *
 * @author changebooks@qq.com
 */
public final class BuilderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuilderUtils.class);

    private BuilderUtils() {
    }

    /**
     * Build WHERE SQL
     *
     * @param pattern   格式
     * @param fieldName 字段名
     * @param value     值
     * @param <T>       the type of the value
     * @return SQL Condition
     */
    public static <T> String build(String pattern, String fieldName, T value) {
        if (value == null) {
            LOGGER.error("build failed, value must not be null, pattern: {}, fieldName: {}", pattern, fieldName);
            return null;
        }

        String placeholder = ValueQuoter.quote(value);
        if (placeholder == null) {
            LOGGER.error("build failed, placeholder must not be empty, pattern: {}, fieldName: {}, value: {}",
                    pattern, fieldName, value);
            return null;
        } else {
            return buildWhere(pattern, fieldName, placeholder);
        }
    }

    /**
     * Build WHERE SQL
     *
     * @param pattern   格式
     * @param fieldName 字段名
     * @return SQL Condition
     */
    public static String build(String pattern, String fieldName) {
        if (pattern == null) {
            LOGGER.error("build failed, pattern must not be null, fieldName: {}", fieldName);
            return null;
        }

        if (pattern.isEmpty()) {
            LOGGER.error("build failed, pattern must not be empty, fieldName: {}", fieldName);
            return null;
        }

        if (fieldName == null) {
            LOGGER.error("build failed, fieldName must not be null, pattern: {}", pattern);
            return null;
        }

        if (fieldName.isEmpty()) {
            LOGGER.error("build failed, fieldName must not be empty, pattern: {}", pattern);
            return null;
        }

        return String.format(pattern, fieldName);
    }

    /**
     * Build WHERE SQL
     *
     * @param pattern   格式
     * @param fieldName 字段名
     * @param value     值
     * @return SQL Condition
     */
    public static String buildWhere(String pattern, String fieldName, String value) {
        if (pattern == null) {
            LOGGER.error("buildWhere failed, pattern must not be null, fieldName: {}, value: {}", fieldName, value);
            return null;
        }

        if (pattern.isEmpty()) {
            LOGGER.error("buildWhere failed, pattern must not be empty, fieldName: {}, value: {}", fieldName, value);
            return null;
        }

        if (fieldName == null) {
            LOGGER.error("buildWhere failed, fieldName must not be null, pattern: {}, value: {}", pattern, value);
            return null;
        }

        if (fieldName.isEmpty()) {
            LOGGER.error("buildWhere failed, fieldName must not be empty, pattern: {}, value: {}", pattern, value);
            return null;
        }

        if (value == null) {
            LOGGER.error("buildWhere failed, value must not be null, pattern: {}, fieldName: {}", pattern, fieldName);
            return null;
        }

        if (value.isEmpty()) {
            LOGGER.error("buildWhere failed, value must not be empty, pattern: {}, fieldName: {}", pattern, fieldName);
            return null;
        }

        return String.format(pattern, fieldName, value);
    }

}
