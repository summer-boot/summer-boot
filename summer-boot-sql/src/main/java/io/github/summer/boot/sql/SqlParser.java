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
