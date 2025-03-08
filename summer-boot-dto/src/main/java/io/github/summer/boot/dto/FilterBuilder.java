package io.github.summer.boot.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public final class FilterBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterBuilder.class);

    private FilterBuilder() {
    }

    /**
     * Build WHERE SQL
     *
     * @param filters list
     * @return [ fieldName = '', fieldName > 0 ]
     */
    public static String[] build(List<BaseFilter> filters) {
        if (filters != null) {
            return filters
                    .stream()
                    .map(FilterBuilder::build)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
        } else {
            return null;
        }
    }

    /**
     * Build WHERE SQL
     *
     * @param filter BaseFilter
     * @return SQL Condition
     */
    public static String build(BaseFilter filter) {
        if (filter == null) {
            LOGGER.error("build failed, filter must not be null");
            return null;
        }

        if (filter instanceof ExpressionFilter) {
            return ExpressionBuilder.build((ExpressionFilter<?>) filter);
        }

        if (filter instanceof RangeFilter) {
            return RangeBuilder.build((RangeFilter<?>) filter);
        }

        if (filter instanceof InFilter) {
            return InBuilder.build((InFilter<?>) filter);
        }

        if (filter instanceof ExistsFilter) {
            return ExistsBuilder.build((ExistsFilter<?>) filter);
        }

        if (filter instanceof NullFilter) {
            return NullBuilder.build((NullFilter) filter);
        }

        if (filter instanceof WildcardFilter) {
            return WildcardBuilder.build((WildcardFilter) filter);
        }

        return null;
    }

}
