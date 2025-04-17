package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public interface FilterParser {
    /**
     * 解析列表
     *
     * @param list [ the {@link BaseFilter} instance ]
     * @return the {@link Statement} instance
     */
    default Statement parse(List<BaseFilter> list) {
        if (list == null) {
            return null;
        }

        List<Statement> statements = list.stream()
                .filter(Objects::nonNull)
                .map(this::parse)
                .filter(Objects::nonNull)
                .toList();

        if (statements.isEmpty()) {
            return null;
        } else {
            return StatementJoiner.join(statements);
        }
    }

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link Statement} instance
     */
    default Statement parse(BaseFilter filter) {
        if (filter == null) {
            return null;
        }

        if (filter instanceof ExpressionFilter expressionFilter) {
            return parseStatement(expressionFilter);
        }

        if (filter instanceof InFilter inFilter) {
            return parseStatement(inFilter);
        }

        if (filter instanceof NullFilter nullFilter) {
            return parseStatement(nullFilter);
        }

        if (filter instanceof RangeFilter rangeFilter) {
            return parseStatement(rangeFilter);
        }

        if (filter instanceof WildcardFilter wildcardFilter) {
            return parseStatement(wildcardFilter);
        }

        if (filter instanceof Filters filters) {
            List<BaseFilter> list = filters.getFilters();
            return parse(list);
        }

        throw new UnsupportedFilterException();
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    default Statement parseStatement(@NotNull ExpressionFilter filter) {
        String sql = parseFilter(filter);
        String logicalOperator = getLogicalOperator(filter);
        Parameter parameter = filter.getParameter();

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter);
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return name = :parameterName, name != :parameterName, name > :parameterName, name < :parameterName
     */
    @NotNull
    String parseFilter(@NotNull ExpressionFilter filter);

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    default Statement parseStatement(@NotNull InFilter filter) {
        String sql = parseFilter(filter);
        String logicalOperator = getLogicalOperator(filter);
        List<Parameter> parameters = filter.getParameters();

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return name IN (:parameterName1, :parameterName2, ...), name NOT IN (:parameterName1, :parameterName2, ...)
     */
    @NotNull
    String parseFilter(@NotNull InFilter filter);

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    default Statement parseStatement(@NotNull NullFilter filter) {
        String sql = parseFilter(filter);
        String logicalOperator = getLogicalOperator(filter);

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator);
    }

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return name IS NULL, name IS NOT NULL
     */
    @NotNull
    String parseFilter(@NotNull NullFilter filter);

    /**
     * 范围、范围开始、范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    default Statement parseStatement(@NotNull RangeFilter filter) {
        String sql = parseFilter(filter);
        String logicalOperator = getLogicalOperator(filter);
        List<Parameter> parameters = filter.getParameters();

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * 范围、范围开始、范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return name >= :parameterName AND name <= :parameterName, name > :parameterName, name < :parameterName
     */
    @NotNull
    String parseFilter(@NotNull RangeFilter filter);

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    default Statement parseStatement(@NotNull WildcardFilter filter) {
        String sql = parseFilter(filter);
        String logicalOperator = getLogicalOperator(filter);
        Parameter parameter = filter.getParameter();

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter);
    }

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return name LIKE CONCAT('%', :parameterName, '%'), name LIKE CONCAT(:parameterName, '%'), name LIKE CONCAT('%', :parameterName)
     */
    @NotNull
    String parseFilter(@NotNull WildcardFilter filter);

    /**
     * 逻辑与或
     *
     * @param filter the {@link BaseFilter} instance
     * @return AND, OR
     */
    @NotNull
    default String getLogicalOperator(@NotNull BaseFilter filter) {
        Boolean or = filter.getOr();
        if (or == null) {
            return "";
        } else {
            return or ? LogicalOperator.OR : LogicalOperator.AND;
        }
    }

}
