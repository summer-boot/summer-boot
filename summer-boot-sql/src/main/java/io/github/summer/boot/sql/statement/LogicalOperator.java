package io.github.summer.boot.sql.statement;

import io.github.summer.boot.filter.BaseFilter;
import jakarta.validation.constraints.NotNull;

/**
 * 逻辑运算符
 *
 * @author changebooks@qq.com
 */
public final class LogicalOperator {

    private LogicalOperator() {
    }

    /**
     * 获取命令，并且前置运算符
     *
     * @param statement the {@link Statement} instance
     * @return AND column = ?, OR column = ?
     */
    @NotNull
    public static String prefixedSql(@NotNull Statement statement) {
        String sql = statement.getSql();
        String logicalOperator = statement.getLogicalOperator();
        return prefixedOperator(sql, logicalOperator);
    }

    /**
     * 前置运算符
     *
     * @param sql             column = ?
     * @param logicalOperator AND, OR
     * @return AND column = ?, OR column = ?
     */
    @NotNull
    public static String prefixedOperator(@NotNull String sql, @NotNull String logicalOperator) {
        if (logicalOperator.isEmpty()) {
            return "AND (" + sql + ")";
        } else {
            return logicalOperator + " (" + sql + ")";
        }
    }

    /**
     * 获取运算符
     *
     * @param filter the {@link BaseFilter} instance
     * @return AND, OR
     */
    @NotNull
    public static String getOperator(@NotNull BaseFilter filter) {
        Boolean or = filter.getOr();
        return getOperator(or);
    }

    /**
     * 获取运算符
     *
     * @param or 逻辑或？
     * @return AND, OR
     */
    @NotNull
    public static String getOperator(Boolean or) {
        if (or == null) {
            return "";
        } else {
            return or ? "OR" : "AND";
        }
    }

}
