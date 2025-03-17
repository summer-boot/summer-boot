package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.statement.LogicalOperator;
import io.github.summer.boot.sql.statement.Statement;
import io.github.summer.boot.sql.statement.StatementJoiner;
import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public class StatementParser {
    /**
     * the {@link FilterParser} instance
     */
    private final FilterParser filterParser;

    public StatementParser() {
        this.filterParser = new FilterParserImpl();
    }

    public StatementParser(FilterParser filterParser) {
        Preconditions.requireNonNull(filterParser, "filterParser must not be null");

        this.filterParser = filterParser;
    }

    /**
     * 解析列表
     *
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link Statement} instance
     */
    public Statement parse(List<BaseFilter> filters) {
        if (filters == null) {
            return null;
        }

        List<Statement> statements = filters.stream()
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
    public Statement parse(BaseFilter filter) {
        if (filter == null) {
            return null;
        }

        if (filter instanceof Filter) {
            return parseSingle((Filter) filter);
        }

        if (filter instanceof Filters) {
            List<BaseFilter> filters = ((Filters) filter).getFilters();
            return parse(filters);
        }

        throw new UnsupportedFilterException();
    }

    /**
     * 解析
     *
     * @param filter the {@link Filter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseSingle(@NotNull Filter filter) {
        if (filter instanceof ExpressionFilter) {
            return parseExpression((ExpressionFilter) filter);
        }

        if (filter instanceof InFilter) {
            return parseIn((InFilter) filter);
        }

        if (filter instanceof NullFilter) {
            return parseNull((NullFilter) filter);
        }

        if (filter instanceof RangeFilter) {
            return parseRange((RangeFilter) filter);
        }

        if (filter instanceof WildcardFilter) {
            return parseWildcard((WildcardFilter) filter);
        }

        String name = filter.getName();
        throw new UnsupportedFilterException(name);
    }

    /**
     * 表达式
     *
     * @param filter the {@link ExpressionFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseExpression(@NotNull ExpressionFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseExpression(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        Parameter parameter = filter.getParameter();

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter)
                .build();
    }

    /**
     * 在列表中？
     *
     * @param filter the {@link InFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseIn(@NotNull InFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseIn(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        List<Parameter> parameters = filter.getParameters();

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters)
                .build();
    }

    /**
     * 空？
     *
     * @param filter the {@link NullFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseNull(@NotNull NullFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseNull(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .build();
    }

    /**
     * 范围
     *
     * @param filter the {@link RangeFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseRange(@NotNull RangeFilter filter) {
        Parameter from = filter.getFrom();
        if (from == null) {
            return parseRangeTo(filter);
        }

        Parameter to = filter.getTo();
        if (to == null) {
            return parseRangeFrom(filter);
        }

        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseRange(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        List<Parameter> parameters = List.of(from, to);

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters)
                .build();
    }

    /**
     * 范围开始
     *
     * @param filter the {@link RangeFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseRangeFrom(@NotNull RangeFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseRangeFrom(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        Parameter parameter = filter.getFrom();

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter)
                .build();
    }

    /**
     * 范围结束
     *
     * @param filter the {@link RangeFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseRangeTo(@NotNull RangeFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseRangeTo(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        Parameter parameter = filter.getTo();

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter)
                .build();
    }

    /**
     * 模糊匹配
     *
     * @param filter the {@link WildcardFilter} instance
     * @return the {@link Statement} instance
     */
    @NotNull
    public Statement parseWildcard(@NotNull WildcardFilter filter) {
        FilterParser filterParser = getFilterParser();
        String sql = filterParser.parseWildcard(filter);
        String logicalOperator = LogicalOperator.getOperator(filter);
        Parameter parameter = filter.getParameter();

        return new Statement.Builder()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameter(parameter)
                .build();
    }

    @NotNull
    public FilterParser getFilterParser() {
        return filterParser;
    }

}
