package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.sql.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class SqlParserImpl implements SqlParser {
    /**
     * the {@link AggregateParser} instance
     */
    private final AggregateParser aggregateParser;

    /**
     * the {@link TableParser} instance
     */
    private final TableParser tableParser;

    /**
     * the {@link WhereParser} instance
     */
    private final WhereParser whereParser;

    /**
     * the {@link GroupParser} instance
     */
    private final GroupParser groupParser;

    /**
     * the {@link OrderParser} instance
     */
    private final OrderParser orderParser;

    /**
     * the {@link PageParser} instance
     */
    private final PageParser pageParser;

    public SqlParserImpl() {
        this.aggregateParser = new AggregateParserImpl();
        this.tableParser = new TableParserImpl();
        this.whereParser = new WhereParserImpl();
        this.groupParser = new GroupParserImpl();
        this.orderParser = new OrderParserImpl();
        this.pageParser = new PageParserImpl();
    }

    @Override
    public SqlParameter parseSelect(String table, List<Table> tables, boolean distinct, String columns, List<BaseFilter> where, Group group, List<Order> orders, Page page) {
        String sql = String.format
                (
                        "SELECT %s%s FROM %s",
                        distinct ? "DISTINCT " : "",
                        columns,
                        table
                );

        SqlParameter sqlParameter = new SqlParameter();
        sqlParameter.setSql(sql);

        joinTable(sqlParameter, tables);
        joinWhere(sqlParameter, where);
        joinGroup(sqlParameter, group);
        joinOrder(sqlParameter, orders);
        joinPage(sqlParameter, page);

        return sqlParameter;
    }

    @Override
    public SqlParameter parseSelect(String table, boolean distinct, String columns, List<BaseFilter> where, List<Order> orders, Page page) {
        String sql = String.format
                (
                        "SELECT %s%s FROM %s",
                        distinct ? "DISTINCT " : "",
                        columns,
                        table
                );

        SqlParameter sqlParameter = new SqlParameter();
        sqlParameter.setSql(sql);

        joinWhere(sqlParameter, where);
        joinOrder(sqlParameter, orders);
        joinPage(sqlParameter, page);

        return sqlParameter;
    }

    @Override
    public String parseInsert(String table, String columns, String values, int batchSize) {
        String joinValues = joinValuePlaceholders(values, batchSize);

        return String.format
                (
                        "INSERT INTO %s (%s) VALUES (%s)",
                        table,
                        columns,
                        joinValues
                );
    }

    @Override
    public SqlParameter parseUpdate(String table, String sets, List<BaseFilter> where) {
        String sql = String.format
                (
                        "UPDATE %s SET %s",
                        table,
                        sets
                );

        SqlParameter sqlParameter = new SqlParameter();
        sqlParameter.setSql(sql);

        joinWhere(sqlParameter, where);

        return sqlParameter;
    }

    @Override
    public String parseUpdate(String table, String sets, String where) {
        String sql = String.format
                (
                        "UPDATE %s SET %s",
                        table,
                        sets
                );

        String prefixedWhere = whereParser.prefixedWhere(where);
        return joinSql(sql, prefixedWhere);
    }

    @Override
    public SqlParameter parseDelete(String table, List<BaseFilter> where) {
        String sql = String.format
                (
                        "DELETE FROM %s",
                        table
                );

        SqlParameter sqlParameter = new SqlParameter();
        sqlParameter.setSql(sql);

        joinWhere(sqlParameter, where);

        return sqlParameter;
    }

    @Override
    public String parseAggregate(Aggregate aggregate) {
        return aggregateParser.parse(aggregate);
    }

    @Override
    public String parseTable(List<Table> list) {
        return tableParser.parse(list);
    }

    @Override
    public String parseTable(Table table) {
        return tableParser.parse(table);
    }

    @Override
    public SqlParameter parseWhere(List<BaseFilter> list) {
        return whereParser.parse(list);
    }

    @Override
    public SqlParameter parseWhere(BaseFilter filter) {
        return whereParser.parse(filter);
    }

    @Override
    public SqlParameter parseGroup(Group group) {
        return groupParser.parse(group);
    }

    @Override
    public String parseOrder(List<Order> list) {
        return orderParser.parse(list);
    }

    @Override
    public String parseOrder(Order order) {
        return orderParser.parse(order);
    }

    @Override
    public String parsePage(Page page) {
        return pageParser.parse(page);
    }

    @Override
    public String parsePageFirst() {
        return pageParser.parseFirst();
    }

    /**
     * Join Sql
     *
     * @param sql     SELECT column, column FROM table
     * @param segment WHERE, ORDER, PAGE
     * @return SELECT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     */
    @NotNull
    public String joinSql(@NotNull String sql, String segment) {
        if (segment == null || segment.isEmpty()) {
            return sql;
        } else {
            return sql + " " + segment;
        }
    }

    /**
     * Join Value Placeholders
     *
     * @param valuePlaceholders ?, ?
     * @param batchSize         Batch Size
     * @return ?, ?), (?, ?
     */
    @NotNull
    public String joinValuePlaceholders(@NotNull String valuePlaceholders, int batchSize) {
        if (batchSize > 1) {
            return String.join("), (", Collections.nCopies(batchSize, valuePlaceholders));
        } else {
            return valuePlaceholders;
        }
    }

}
