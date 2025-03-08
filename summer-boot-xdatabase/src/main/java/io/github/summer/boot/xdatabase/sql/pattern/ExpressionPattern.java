package io.github.summer.boot.xdatabase.sql.pattern;

import io.github.summer.boot.filter.ExpressionFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 表达式
 *
 * @author changebooks@qq.com
 */
public final class ExpressionPattern {

    private ExpressionPattern() {
    }

    /**
     * 格式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return %s = %s, %s != %s, %s > %s, %s < %s, %s >= %s, %s <= %s
     */
    @NotNull
    public static String getPattern(@NotNull ExpressionFilter filter) {
        Expression expression = getExpression(filter);
        return expression.pattern;
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return Expression.NULL, Expression.EQ, Expression.NE, Expression.GT, Expression.LT, Expression.GE, Expression.LE
     */
    @NotNull
    public static Expression getExpression(@NotNull ExpressionFilter filter) {
        int code = filter.getCode();
        return Expression.forCode(code);
    }

}
