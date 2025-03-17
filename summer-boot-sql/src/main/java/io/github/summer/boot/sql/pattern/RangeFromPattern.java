package io.github.summer.boot.sql.pattern;

import io.github.summer.boot.filter.RangeFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 范围开始
 *
 * @author changebooks@qq.com
 */
public final class RangeFromPattern {

    private RangeFromPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link RangeFilter} instance
     * @return %s >= %s, %s > %s
     */
    @NotNull
    public static String getPattern(@NotNull RangeFilter filter) {
        Expression expression = getExpression(filter);
        return expression.pattern;
    }

    /**
     * 表达式
     *
     * @param filter the {@link RangeFilter} instance
     * @return Expression.GE, Expression.GT
     */
    @NotNull
    public static Expression getExpression(@NotNull RangeFilter filter) {
        boolean includeLower = filter.isIncludeLower();
        return includeLower ? Expression.GE : Expression.GT;
    }

}
