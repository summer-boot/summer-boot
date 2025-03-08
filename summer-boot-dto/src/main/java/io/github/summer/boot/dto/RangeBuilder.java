package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 范围
 *
 * @author changebooks@qq.com
 */
public final class RangeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RangeBuilder.class);

    /**
     * 格式
     */
    private static final String PATTERN = "(%s AND %s)";

    private RangeBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter RangeFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    public static <T> String build(RangeFilter<T> filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        String fromWhere = buildFrom(filter);
        String toWhere = buildTo(filter);

        if (fromWhere == null || fromWhere.isEmpty()) {
            return toWhere;
        }

        if (toWhere == null || toWhere.isEmpty()) {
            return fromWhere;
        }

        return String.format(PATTERN, fromWhere, toWhere);
    }

    /**
     * Build WHERE SQL
     *
     * @param filter RangeFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    private static <T> String buildFrom(RangeFilter<T> filter) {
        T value = filter.getFrom();
        if (value == null) {
            return null;
        }

        Expression expression = filter.isIncludeLower() ? Expression.GE : Expression.GT;
        String pattern = expression.pattern;
        String fieldName = filter.getFieldName();

        return BuilderUtils.build(pattern, fieldName, value);
    }

    /**
     * Build WHERE SQL
     *
     * @param filter RangeFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    private static <T> String buildTo(RangeFilter<T> filter) {
        T value = filter.getTo();
        if (value == null) {
            return null;
        }

        Expression expression = filter.isIncludeUpper() ? Expression.LE : Expression.LT;
        String pattern = expression.pattern;
        String fieldName = filter.getFieldName();

        return BuilderUtils.build(pattern, fieldName, value);
    }

}
