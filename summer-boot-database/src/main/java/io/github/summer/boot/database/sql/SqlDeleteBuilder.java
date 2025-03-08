package io.github.summer.boot.database.sql;

import org.apache.ibatis.jdbc.SQL;

/**
 * Build DELETE SQL
 *
 * @author changebooks@qq.com
 */
public class SqlDeleteBuilder implements SqlBuilder.SqlDelete {
    /**
     * the table name
     * e.g. table_name, table_name_${tableNum}
     */
    private final String table;

    /**
     * the id condition
     * e.g. [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    private final String[] whereId;

    public SqlDeleteBuilder(String table, String[] whereId) {
        this.table = table;
        this.whereId = whereId;
    }

    @Override
    public SQL deleteOne() {
        return new SQL() {
            {
                DELETE_FROM(getTable());
                WHERE(getWhereId());
            }
        };
    }

    public String getTable() {
        return table;
    }

    public String[] getWhereId() {
        return whereId;
    }

}
