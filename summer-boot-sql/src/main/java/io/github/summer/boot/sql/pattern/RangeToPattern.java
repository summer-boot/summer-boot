package io.github.summer.boot.sql.pattern;

import io.github.summer.boot.filter.RangeFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 范围结束
 *
 * @author changebooks@qq.com
 */
public final class RangeToPattern {

    private RangeToPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link RangeFilter} instance
     * @return %s <= %s, %s < %s
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
     * @return Expression.LE, Expression.LT
     */
    @NotNull
    public static Expression getExpression(@NotNull RangeFilter filter) {
        boolean includeUpper = filter.isIncludeUpper();
        return includeUpper ? Expression.LE : Expression.LT;
    }

}
