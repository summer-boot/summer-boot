package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Statement;
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

    @NotNull
    @Override
    public SqlParameter parseSelect(@NotNull String table, List<Table> tables,
                                    boolean distinct, @NotNull String columns,
                                    List<BaseFilter> where, Group group, List<Order> orders, Page page) {
        return null;
    }

    @NotNull
    @Override
    public String parseInsert(@NotNull String table, @NotNull String columns, @NotNull String values, int batchSize) {
        return null;
    }

    @NotNull
    @Override
    public SqlParameter parseUpdate(@NotNull String table, @NotNull String sets, List<BaseFilter> where) {
        return null;
    }

    @NotNull
    @Override
    public String parseUpdate(@NotNull String table, @NotNull String sets, String where) {
        return null;
    }

    @NotNull
    @Override
    public SqlParameter parseDelete(@NotNull String table, List<BaseFilter> where) {
        return null;
    }

    @Override
    public String parseAggregate(Aggregate aggregate) {
        AggregateParser aggregateParser = getAggregateParser();
        return aggregateParser.parse(aggregate);
    }

    @Override
    public String parseTable(List<Table> list) {
        TableParser tableParser = getTableParser();
        return tableParser.parse(list);
    }

    @Override
    public String parseTable(Table table) {
        TableParser tableParser = getTableParser();
        return tableParser.parse(table);
    }

    @Override
    public String prefixedWhere(String sql) {
        WhereParser whereParser = getWhereParser();
        return whereParser.prefixed(sql);
    }

    @Override
    public Statement parseWhere(List<BaseFilter> list) {
        WhereParser whereParser = getWhereParser();
        return whereParser.parse(list);
    }

    @Override
    public Statement parseWhere(BaseFilter filter) {
        WhereParser whereParser = getWhereParser();
        return whereParser.parse(filter);
    }

    @Override
    public Statement parseGroup(Group group) {
        GroupParser groupParser = getGroupParser();
        return groupParser.parse(group);
    }

    @Override
    public String parseOrder(List<Order> list) {
        OrderParser orderParser = getOrderParser();
        return orderParser.parse(list);
    }

    @Override
    public String parseOrder(Order order) {
        OrderParser orderParser = getOrderParser();
        return orderParser.parse(order);
    }

    @Override
    public String parsePage(Page page) {
        PageParser pageParser = getPageParser();
        return pageParser.parse(page);
    }

    @Override
    public String parsePageFirst() {
        PageParser pageParser = getPageParser();
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

    public AggregateParser getAggregateParser() {
        return aggregateParser;
    }

    public TableParser getTableParser() {
        return tableParser;
    }

    public WhereParser getWhereParser() {
        return whereParser;
    }

    public GroupParser getGroupParser() {
        return groupParser;
    }

    public OrderParser getOrderParser() {
        return orderParser;
    }

    public PageParser getPageParser() {
        return pageParser;
    }

}
