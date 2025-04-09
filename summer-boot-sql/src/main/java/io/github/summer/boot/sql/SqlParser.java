package io.github.summer.boot.sql;

import io.github.summer.boot.filter.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 命令和参数
 *
 * @author changebooks@qq.com
 */
public interface SqlParser {
    /**
     * SELECT DISTINCT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     *
     * @param table    FROM table
     * @param distinct DISTINCT ?
     * @param columns  column, COUNT(*) AS aggregate, 1
     * @param filters  [ the {@link BaseFilter} instance ]
     * @param orders   [ the {@link Order} instance ]
     * @param page     the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseSelect(@NotNull String table,
                             boolean distinct, @NotNull String columns,
                             List<BaseFilter> filters, List<Order> orders, Page page);

    /**
     * SELECT DISTINCT column, COUNT(*), SUM(column), MAX(column), MIN(column), AVG(column) FROM table GROUP BY name HAVING column = ? WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     *
     * @param table    FROM table
     * @param distinct DISTINCT ?
     * @param columns  column, COUNT(*), SUM(column), MAX(column), MIN(column), AVG(column)
     * @param groups   [ the {@link Group} instance ]
     * @param having   [ the {@link BaseFilter} instance ]
     * @param where    [ the {@link BaseFilter} instance ]
     * @param orders   [ the {@link Order} instance ]
     * @param page     the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseSelect(@NotNull String table,
                             boolean distinct, @NotNull List<AggregateFunc> columns,
                             List<Group> groups, List<BaseFilter> having,
                             List<BaseFilter> where, List<Order> orders, Page page);

    /**
     * SELECT DISTINCT table1.column, COUNT(*), SUM(table1.column), MAX(table1.column), MIN(table2.column), AVG(table2.column) FROM table1 JOIN table2 ON table1.column = table2.column GROUP BY table1.name HAVING table1.column = ? WHERE table1.column = ? ORDER BY table1.name ASC LIMIT offset, limit
     *
     * @param table      FROM table
     * @param joinTables [ the {@link JoinTable} instance ]
     * @param distinct   DISTINCT ?
     * @param columns    table1.column, COUNT(*), SUM(table1.column), MAX(table1.column), MIN(table2.column), AVG(table2.column)
     * @param groups     [ the {@link Group} instance ]
     * @param having     [ the {@link BaseFilter} instance ]
     * @param where      [ the {@link BaseFilter} instance ]
     * @param orders     [ the {@link Order} instance ]
     * @param page       the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseSelect(@NotNull String table, @NotNull List<JoinTable> joinTables,
                             boolean distinct, @NotNull List<AggregateFunc> columns,
                             List<Group> groups, List<BaseFilter> having,
                             List<BaseFilter> where, List<Order> orders, Page page);

    /**
     * INSERT INTO table (column, column) VALUES (?, ?), (?, ?), (?, ?)
     *
     * @param table     INSERT INTO table
     * @param columns   column, column
     * @param values    ?, ?
     * @param batchSize Batch Size
     * @return Parsed SQL
     */
    @NotNull
    String parseInsert(@NotNull String table,
                       @NotNull String columns, @NotNull String values, int batchSize);

    /**
     * UPDATE table SET column = ?, column = column + 1 WHERE column = ?
     *
     * @param table   UPDATE table
     * @param sets    column = ?, column = column + 1
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseUpdate(@NotNull String table,
                             @NotNull String sets, List<BaseFilter> filters);

    /**
     * UPDATE table SET column = ?, column = column + 1 WHERE column = ?
     *
     * @param table UPDATE table
     * @param sets  column = ?, column = column + 1
     * @param where column = ?
     * @return Parsed SQL
     */
    @NotNull
    String parseUpdate(@NotNull String table,
                       @NotNull String sets, String where);

    /**
     * DELETE FROM table WHERE column = ?
     *
     * @param table   DELETE FROM table
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseDelete(@NotNull String table, List<BaseFilter> filters);

    /**
     * Group Parser
     *
     * @return the {@link GroupParser} instance
     */
    @NotNull
    GroupParser getGroupParser();

    /**
     * Having Parser
     *
     * @return the {@link HavingParser} instance
     */
    @NotNull
    HavingParser getHavingParser();

    /**
     * Where Parser
     *
     * @return the {@link WhereParser} instance
     */
    @NotNull
    WhereParser getWhereParser();

    /**
     * Order Parser
     *
     * @return the {@link OrderParser} instance
     */
    @NotNull
    OrderParser getOrderParser();

    /**
     * Page Parser
     *
     * @return the {@link PageParser} instance
     */
    @NotNull
    PageParser getPageParser();

}
