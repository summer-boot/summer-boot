package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * 内部，命令和参数
 *
 * @param filterParser the {@link FilterParser} instance
 * @author changebooks@qq.com
 */
record InternalStatementParser(FilterParser filterParser) {

    InternalStatementParser(@NotNull FilterParser filterParser) {
        this.filterParser = filterParser;
    }

    /**
     * 解析列表
     *
     * @param list [ the {@link BaseFilter} instance ]
     * @return the {@link InternalStatement} instance
     */
    public InternalStatement parseList(List<BaseFilter> list) {
        if (list == null) {
            return null;
        }

        List<InternalStatement> statements = list.stream()
                .filter(Objects::nonNull)
                .map(this::parseOne)
                .filter(Objects::nonNull)
                .toList();

        if (statements.isEmpty()) {
            return null;
        } else {
            return InternalStatementJoiner.join(statements);
        }
    }

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link InternalStatement} instance
     */
    public InternalStatement parseOne(BaseFilter filter) {
        if (filter == null) {
            return null;
        }

        if (filter instanceof ExpressionFilter expressionFilter) {
            return parseExpression(expressionFilter);
        }

        if (filter instanceof InFilter inFilter) {
            return parseIn(inFilter);
        }

        if (filter instanceof NullFilter nullFilter) {
            return parseNull(nullFilter);
        }

        if (filter instanceof RangeFilter rangeFilter) {
            return parseRange(rangeFilter);
        }

        if (filter instanceof WildcardFilter wildcardFilter) {
            return parseWildcard(wildcardFilter);
        }

        if (filter instanceof Filters filters) {
            List<BaseFilter> list = filters.getFilters();
            return parseList(list);
        }

        throw new UnsupportedFilterException();
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    public InternalStatement parseExpression(@NotNull ExpressionFilter filter) {
        FilterParser filterParser = filterParser();

        String sql = filterParser.parseExpression(filter);
        String logicalOperator = getLogicalOperator(filter);
        Parameter parameter = filter.getParameter();

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter);
    }

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    public InternalStatement parseIn(@NotNull InFilter filter) {
        FilterParser filterParser = filterParser();

        String sql = filterParser.parseIn(filter);
        String logicalOperator = getLogicalOperator(filter);
        List<Parameter> parameters = filter.getParameters();

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    public InternalStatement parseNull(@NotNull NullFilter filter) {
        FilterParser filterParser = filterParser();

        String sql = filterParser.parseNull(filter);
        String logicalOperator = getLogicalOperator(filter);

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator);
    }

    /**
     * 范围、范围开始、范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    public InternalStatement parseRange(@NotNull RangeFilter filter) {
        FilterParser filterParser = filterParser();

        String sql = filterParser.parseRange(filter);
        String logicalOperator = getLogicalOperator(filter);
        List<Parameter> parameters = filter.getParameters();

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    public InternalStatement parseWildcard(@NotNull WildcardFilter filter) {
        FilterParser filterParser = filterParser();

        String sql = filterParser.parseWildcard(filter);
        String logicalOperator = getLogicalOperator(filter);
        Parameter parameter = filter.getParameter();

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter);
    }

    /**
     * 逻辑与或
     *
     * @param filter the {@link BaseFilter} instance
     * @return AND, OR
     */
    @NotNull
    public String getLogicalOperator(@NotNull BaseFilter filter) {
        Boolean or = filter.getOr();
        if (or == null) {
            return "";
        } else {
            return or ? LogicalOperator.OR : LogicalOperator.AND;
        }
    }

    @Override
    @NotNull
    public FilterParser filterParser() {
        return filterParser;
    }

}
