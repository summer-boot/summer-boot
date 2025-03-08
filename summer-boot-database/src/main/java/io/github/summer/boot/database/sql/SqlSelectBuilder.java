package io.github.summer.boot.database.sql;

import org.apache.ibatis.jdbc.SQL;

/**
 * Build SELECT SQL
 *
 * @author changebooks@qq.com
 */
public class SqlSelectBuilder implements SqlBuilder.SqlSelect {
    /**
     * the table name
     * e.g. table_name, table_name_${tableNum}
     */
    private final String table;

    /**
     * the column names
     * e.g. [ column_name, column_name ]
     */
    private final String[] columns;

    /**
     * the id condition
     * e.g. [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    private final String[] whereId;

    public SqlSelectBuilder(String table, String[] columns, String[] whereId) {
        this.table = table;
        this.columns = columns;
        this.whereId = whereId;
    }

    @Override
    public SQL selectList(String[] conditions, String[] orders, Long startRow, Integer pageSize) {
        return new SQL() {
            {
                SELECT(getColumns());
                FROM(getTable());

                if (conditions != null) {
                    WHERE(conditions);
                }

                if (orders != null) {
                    ORDER_BY(orders);
                }

                if (pageSize != null) {
                    LIMIT(pageSize);

                    if (startRow != null) {
                        OFFSET(startRow);
                    }
                }
            }
        };
    }

    @Override
    public SQL selectCount(String[] conditions) {
        return new SQL() {
            {
                SELECT(COUNT);
                FROM(getTable());

                if (conditions != null) {
                    WHERE(conditions);
                }
            }
        };
    }

    @Override
    public SQL selectOne() {
        return new SQL() {
            {
                SELECT(getColumns());
                FROM(getTable());
                WHERE(getWhereId());
            }
        };
    }

    @Override
    public SQL checkExist(String[] conditions) {
        return new SQL() {
            {
                SELECT("1");
                FROM(getTable());

                if (conditions != null) {
                    WHERE(conditions);
                }

                LIMIT(1);
            }
        };
    }

    public String getTable() {
        return table;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getWhereId() {
        return whereId;
    }

}
