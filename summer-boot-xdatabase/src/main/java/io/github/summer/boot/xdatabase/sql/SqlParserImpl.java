package io.github.summer.boot.xdatabase.sql;

import io.github.summer.boot.filter.*;
import io.github.summer.boot.value.Parameter;
import io.github.summer.boot.xdatabase.Preconditions;
import io.github.summer.boot.xdatabase.SqlParameter;
import io.github.summer.boot.xdatabase.SqlParameterParser;
import io.github.summer.boot.xdatabase.SqlParser;
import io.github.summer.boot.xdatabase.sql.filter.OrderParserImpl;
import io.github.summer.boot.xdatabase.sql.filter.PageParserImpl;
import io.github.summer.boot.xdatabase.sql.filter.WhereParserImpl;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class SqlParserImpl implements SqlParser {
    /**
     * the {@link WhereParser} instance
     */
    private final WhereParser whereParser;

    /**
     * the {@link OrderParser} instance
     */
    private final OrderParser orderParser;

    /**
     * the {@link PageParser} instance
     */
    private final PageParser pageParser;

    public SqlParserImpl() {
        this.whereParser = new WhereParserImpl();
        this.orderParser = new OrderParserImpl();
        this.pageParser = new PageParserImpl();
    }

    public SqlParserImpl(WhereParser whereParser, OrderParser orderParser, PageParser pageParser) {
        Preconditions.requireNonNull(whereParser, "whereParser must not be null");
        Preconditions.requireNonNull(orderParser, "orderParser must not be null");
        Preconditions.requireNonNull(pageParser, "pageParser must not be null");

        this.whereParser = whereParser;
        this.orderParser = orderParser;
        this.pageParser = pageParser;
    }

    @Override
    public @NotNull SqlParameter parseSelect(@NotNull String table,
                                             boolean distinct, @NotNull String columns,
                                             List<BaseFilter> filters, List<Order> orders, Page page) {
        String sql = String.format
                (
                        "SELECT %s%s FROM %s",
                        distinct ? "DISTINCT " : "",
                        columns,
                        table
                );

        Where where = parseWhere(filters);

        sql = joinWhere(sql, where);
        sql = joinOrder(sql, orders);
        sql = joinPage(sql, page);

        return parseSqlParameter(sql, where);
    }

    @Override
    public @NotNull String parseInsert(@NotNull String table,
                                       @NotNull String columns, @NotNull String values, int batchSize) {
        String joinValues = SqlJoiner.joinValuePlaceholders(values, batchSize);

        return String.format
                (
                        "INSERT INTO %s (%s) VALUES (%s)",
                        table,
                        columns,
                        joinValues
                );
    }

    @Override
    public @NotNull SqlParameter parseUpdate(@NotNull String table,
                                             @NotNull String sets, List<BaseFilter> filters) {
        String sql = String.format
                (
                        "UPDATE %s SET %s",
                        table,
                        sets
                );

        Where where = parseWhere(filters);
        sql = joinWhere(sql, where);

        return parseSqlParameter(sql, where);
    }

    @Override
    public @NotNull String parseUpdate(@NotNull String table,
                                       @NotNull String sets, String where) {
        String sql = String.format
                (
                        "UPDATE %s SET %s",
                        table,
                        sets
                );

        return joinWhereAndPrefixed(sql, where);
    }

    @Override
    public @NotNull SqlParameter parseDelete(@NotNull String table, List<BaseFilter> filters) {
        String sql = String.format
                (
                        "DELETE FROM %s",
                        table
                );

        Where where = parseWhere(filters);
        sql = joinWhere(sql, where);

        return parseSqlParameter(sql, where);
    }

    /**
     * Parse SqlParameter
     *
     * @param sql   SELECT FROM table WHERE column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     * @param where the {@link Where} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    public SqlParameter parseSqlParameter(@NotNull String sql, Where where) {
        if (where == null) {
            return SqlParameterParser.parse(sql, null);
        } else {
            List<Parameter> parameters = where.getParameters();
            return SqlParameterParser.parse(sql, parameters);
        }
    }

    /**
     * Parse Where
     *
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link Where} instance
     */
    public Where parseWhere(List<BaseFilter> filters) {
        WhereParser whereParser = getWhereParser();
        return whereParser.parseWhere(filters);
    }

    /**
     * Join Where
     *
     * @param sql   SELECT column, column FROM table
     * @param where column = ?
     * @return SELECT column, column FROM table WHERE column = ?
     */
    @NotNull
    protected String joinWhereAndPrefixed(@NotNull String sql, String where) {
        WhereParser whereParser = getWhereParser();
        String whereSql = whereParser.prefixedWhere(where);
        return SqlJoiner.joinWhere(sql, whereSql);
    }

    /**
     * Join Where
     *
     * @param sql   SELECT column, column FROM table
     * @param where the {@link Where} instance
     * @return SELECT column, column FROM table WHERE column = ?
     */
    @NotNull
    protected String joinWhere(@NotNull String sql, Where where) {
        if (where == null) {
            return sql;
        } else {
            String whereSql = where.getSql();
            return SqlJoiner.joinWhere(sql, whereSql);
        }
    }

    /**
     * Join Order
     *
     * @param sql    SELECT column, column FROM table
     * @param orders [ the {@link Order} instance ]
     * @return SELECT column, column FROM table ORDER BY name ASC
     */
    @NotNull
    protected String joinOrder(@NotNull String sql, List<Order> orders) {
        OrderParser orderParser = getOrderParser();
        String orderSql = orderParser.parseOrder(orders);
        return SqlJoiner.joinOrder(sql, orderSql);
    }

    /**
     * Join Page
     *
     * @param sql  SELECT column, column FROM table
     * @param page the {@link Page} instance
     * @return SELECT column, column FROM table LIMIT offset, limit
     */
    @NotNull
    protected String joinPage(@NotNull String sql, Page page) {
        PageParser pageParser = getPageParser();
        String pageSql = pageParser.parsePage(page);
        return SqlJoiner.joinPage(sql, pageSql);
    }

    /**
     * Join First Page
     *
     * @param sql SELECT column, column FROM table
     * @return SELECT column, column FROM table LIMIT 1
     */
    @NotNull
    protected String joinFirstPage(@NotNull String sql) {
        PageParser pageParser = getPageParser();
        String pageSql = pageParser.parseFirstPage();
        return SqlJoiner.joinPage(sql, pageSql);
    }

    @Override
    public WhereParser getWhereParser() {
        return whereParser;
    }

    @Override
    public OrderParser getOrderParser() {
        return orderParser;
    }

    @Override
    public PageParser getPageParser() {
        return pageParser;
    }

}
