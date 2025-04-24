package io.github.summer.boot.sql;

import io.github.summer.boot.filter.BaseFilter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 解析命令
 *
 * @author changebooks@qq.com
 */
public interface SqlParser {
    /**
     * SELECT DISTINCT table1.column1, COUNT(1), SUM(table1.column1), MAX(table2.column2), MIN(table2.column2), AVG(table3.column3)
     * FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table1.column1 = table3.column3
     * WHERE table1.column1 = :parameterName AND table2.column2 IS NOT NULL OR (table3.column3 >= :parameterName AND table3.column3 <= :parameterName)
     * GROUP BY table1.column1, table2.column2 HAVING SUM(table1.column1) >= :parameterName AND MAX(table2.column2) <= :parameterName
     * ORDER BY SUM(table1.column1), MAX(table2.column2) ASC
     * LIMIT offset, limit
     *
     * @param table    FROM table
     * @param tables   [ the {@link Table} instance ]
     * @param distinct DISTINCT ?
     * @param columns  table1.column1, COUNT(1), SUM(table1.column1), MAX(table2.column2), MIN(table2.column2), AVG(table3.column3)
     * @param where    [ the {@link BaseFilter} instance ]
     * @param group    the {@link Group} instance
     * @param orders   [ the {@link Order} instance ]
     * @param page     the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseSelect(@NotNull String table, List<Table> tables,
                             boolean distinct, @NotNull String columns,
                             List<BaseFilter> where, Group group,
                             List<Order> orders, Page page);

    /**
     * SELECT DISTINCT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     *
     * @param table    FROM table
     * @param distinct DISTINCT ?
     * @param columns  column, COUNT(*) AS aggregate, 1
     * @param where    [ the {@link BaseFilter} instance ]
     * @param orders   [ the {@link Order} instance ]
     * @param page     the {@link Page} instance
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseSelect(@NotNull String table,
                             boolean distinct, @NotNull String columns,
                             List<BaseFilter> where,
                             List<Order> orders, Page page);

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
     * @param table UPDATE table
     * @param sets  column = ?, column = column + 1
     * @param where [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseUpdate(@NotNull String table,
                             @NotNull String sets, List<BaseFilter> where);

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
     * @param table DELETE FROM table
     * @param where [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    SqlParameter parseDelete(@NotNull String table, List<BaseFilter> where);

    /**
     * 解析聚合函数
     *
     * @param aggregate the {@link Aggregate} instance
     * @return COUNT(1), SUM(column), MAX(column), MIN(column), AVG(column)
     */
    String parseAggregate(Aggregate aggregate);

    /**
     * 连表
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param list         [ the {@link Table} instance ]
     */
    default void joinTable(@NotNull SqlParameter sqlParameter, List<Table> list) {
        String sql = parseTable(list);
        sqlParameter.joinSql(sql);
    }

    /**
     * 解析连表
     *
     * @param list [ the {@link Table} instance ]
     * @return LEFT JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table1.column1 = table3.column3
     */
    String parseTable(List<Table> list);

    /**
     * 解析连表
     *
     * @param table the {@link Table} instance
     * @return LEFT JOIN table2 ON table1.column1 = table2.column1 AND table1.column2 = table2.column2
     */
    String parseTable(Table table);

    /**
     * 连接条件
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param list         [ the {@link BaseFilter} instance ]
     */
    default void joinWhere(@NotNull SqlParameter sqlParameter, List<BaseFilter> list) {
        SqlParameter parsedWhere = parseWhere(list);
        sqlParameter.join(parsedWhere);
    }

    /**
     * 解析条件
     *
     * @param list [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parseWhere(List<BaseFilter> list);

    /**
     * 解析条件
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parseWhere(BaseFilter filter);

    /**
     * 连接分组
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param group        the {@link Group} instance
     */
    default void joinGroup(@NotNull SqlParameter sqlParameter, Group group) {
        SqlParameter parsedGroup = parseGroup(group);
        sqlParameter.join(parsedGroup);
    }

    /**
     * 解析分组
     *
     * @param group the {@link Group} instance
     * @return the {@link SqlParameter} instance
     */
    SqlParameter parseGroup(Group group);

    /**
     * 连接排序
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param list         [ the {@link Order} instance ]
     */
    default void joinOrder(@NotNull SqlParameter sqlParameter, List<Order> list) {
        String sql = parseOrder(list);
        sqlParameter.joinSql(sql);
    }

    /**
     * 解析排序
     *
     * @param list [ the {@link Order} instance ]
     * @return ORDER BY name, name ASC, name DESC
     */
    String parseOrder(List<Order> list);

    /**
     * 解析排序
     *
     * @param order the {@link Order} instance
     * @return ORDER BY name, ORDER BY name ASC, ORDER BY name DESC
     */
    String parseOrder(Order order);

    /**
     * 连接分页
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param page         the {@link Page} instance
     */
    default void joinPage(@NotNull SqlParameter sqlParameter, Page page) {
        String sql = parsePage(page);
        sqlParameter.joinSql(sql);
    }

    /**
     * 解析分页
     *
     * @param page the {@link Page} instance
     * @return LIMIT offset, limit
     */
    String parsePage(Page page);

    /**
     * 连接分页首页
     *
     * @param sqlParameter the {@link SqlParameter} instance
     */
    default void joinPageFirst(@NotNull SqlParameter sqlParameter) {
        String sql = parsePageFirst();
        sqlParameter.joinSql(sql);
    }

    /**
     * 解析分页首页
     *
     * @return LIMIT 1
     */
    String parsePageFirst();

}
