package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.sql.*;
import jakarta.validation.constraints.NotNull;

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
    public SqlParameter parseSelect(@NotNull String table, List<Table> tables, boolean distinct, @NotNull String columns, List<BaseFilter> where, Group group, List<Order> orders, Page page) {
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
        return null;
    }

    @Override
    public String parseTable(List<Table> list) {
        return null;
    }

    @Override
    public String parseTable(Table table) {
        return null;
    }

    @Override
    public String prefixedWhere(String sql) {
        return null;
    }

    @Override
    public Statement parseWhere(List<BaseFilter> list) {
        return null;
    }

    @Override
    public Statement parseWhere(BaseFilter filter) {
        return null;
    }

    @Override
    public Statement parseGroup(Group group) {
        return null;
    }

    @Override
    public String parseOrder(List<Order> list) {
        return null;
    }

    @Override
    public String parseOrder(Order order) {
        return null;
    }

    @Override
    public String parsePage(Page page) {
        return null;
    }

    @Override
    public String parsePageFirst() {
        return null;
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
