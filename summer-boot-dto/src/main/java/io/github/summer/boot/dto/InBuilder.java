package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 在列表中？
 *
 * @author changebooks@qq.com
 */
public final class InBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(InBuilder.class);

    /**
     * 在列表中
     */
    private static final String PATTERN = "%s IN (%s)";

    /**
     * 不在列表
     */
    private static final String PATTERN_NOT = "%s NOT IN (%s)";

    private InBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filter InFilter
     * @param <T>    the type of the value
     * @return SQL Condition
     */
    public static <T> String build(InFilter<T> filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        List<T> values = filter.getValues();
        if (values == null) {
            LOGGER.error("build failed, values must not be null, filter: {}", filter);
            return null;
        }

        if (values.isEmpty()) {
            LOGGER.error("build failed, values must not be empty, filter: {}", filter);
            return null;
        }

        String pattern = filter.isNot() ? PATTERN_NOT : PATTERN;
        String fieldName = filter.getFieldName();
        String placeholder = ValueQuoter.join(values);

        return BuilderUtils.buildWhere(pattern, fieldName, placeholder);
    }

}
