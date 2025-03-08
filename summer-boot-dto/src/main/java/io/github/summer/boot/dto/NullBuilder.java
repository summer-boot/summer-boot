package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 空？
 *
 * @author changebooks@qq.com
 */
public final class NullBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(NullBuilder.class);

    /**
     * 空
     */
    private static final String PATTERN = "%s IS NULL";

    /**
     * 非空
     */
    private static final String PATTERN_NOT = "%s IS NOT NULL";

    private NullBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter NullFilter
     * @return SQL Condition
     */
    public static String build(NullFilter filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        String pattern = filter.isNot() ? PATTERN_NOT : PATTERN;
        String fieldName = filter.getFieldName();

        return BuilderUtils.build(pattern, fieldName);
    }

}
