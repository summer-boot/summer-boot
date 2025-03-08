package io.github.summer.boot.xdatabase.sql;

import jakarta.validation.constraints.NotNull;

import java.util.Collections;

/**
 * 命令
 *
 * @author changebooks@qq.com
 */
public final class SqlJoiner {

    private SqlJoiner() {
    }

    /**
     * Join Where
     *
     * @param sql   SELECT column, column FROM table
     * @param where WHERE column = ?
     * @return SELECT column, column FROM table WHERE column = ?
     */
    @NotNull
    public static String joinWhere(@NotNull String sql, String where) {
        if (where == null) {
            return sql;
        } else {
            return joinSql(sql, where);
        }
    }

    /**
     * Join Order
     *
     * @param sql   SELECT column, column FROM table
     * @param order ORDER BY name ASC
     * @return SELECT column, column FROM table ORDER BY name ASC
     */
    @NotNull
    public static String joinOrder(@NotNull String sql, String order) {
        if (order == null) {
            return sql;
        } else {
            return joinSql(sql, order);
        }
    }

    /**
     * Join Page
     *
     * @param sql  SELECT column, column FROM table
     * @param page LIMIT offset, limit
     * @return SELECT column, column FROM table LIMIT offset, limit
     */
    @NotNull
    public static String joinPage(@NotNull String sql, String page) {
        if (page == null) {
            return sql;
        } else {
            return joinSql(sql, page);
        }
    }

    /**
     * Join Sql
     *
     * @param sql     SELECT column, column FROM table
     * @param segment WHERE, ORDER, PAGE
     * @return SELECT column, column FROM table WHERE column = ? ORDER BY name ASC LIMIT offset, limit
     */
    @NotNull
    public static String joinSql(@NotNull String sql, @NotNull String segment) {
        if (segment.isEmpty()) {
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
    public static String joinValuePlaceholders(@NotNull String valuePlaceholders, int batchSize) {
        if (batchSize > 1) {
            return String.join("), (", Collections.nCopies(batchSize, valuePlaceholders));
        } else {
            return valuePlaceholders;
        }
    }

}
