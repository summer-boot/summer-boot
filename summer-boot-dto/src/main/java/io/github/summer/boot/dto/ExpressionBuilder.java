package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public class ExpressionBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionBuilder.class);

    private ExpressionBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter ExpressionFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    public static <T> String build(ExpressionFilter<T> filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        Integer code = filter.getCode();
        if (code == null) {
            LOGGER.error("build failed, code must not be null, filter: {}", filter);
            return null;
        }

        Expression expression = Expression.forCode(code);
        if (expression == null) {
            LOGGER.error("build failed, expression must not be null, code: {}, filter: {}", code, filter);
            return null;
        }

        String pattern = expression.pattern;
        String fieldName = filter.getFieldName();
        Object value = filter.getValue();

        return BuilderUtils.build(pattern, fieldName, value);
    }

}
