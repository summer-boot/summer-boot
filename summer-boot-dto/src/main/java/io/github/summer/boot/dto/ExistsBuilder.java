package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有值？
 *
 * @author changebooks@qq.com
 */
public final class ExistsBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExistsBuilder.class);

    /**
     * 有值
     */
    private static final String PATTERN = "%s != %s";

    /**
     * 无值
     */
    private static final String PATTERN_NOT = "%s = %s";

    private ExistsBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter ExistsFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    public static <T> String build(ExistsFilter<T> filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        String pattern = filter.isNot() ? PATTERN_NOT : PATTERN;
        String fieldName = filter.getFieldName();
        Object defaultValue = filter.getDefaultValue();

        return BuilderUtils.build(pattern, fieldName, defaultValue);
    }

}
