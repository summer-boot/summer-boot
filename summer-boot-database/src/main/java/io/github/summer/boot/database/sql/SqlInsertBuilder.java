package io.github.summer.boot.database.sql;

import org.apache.ibatis.jdbc.SQL;

/**
 * Build INSERT SQL
 *
 * @author changebooks@qq.com
 */
public class SqlInsertBuilder implements SqlBuilder.SqlInsert {
    /**
     * the table name
     * e.g. table_name, table_name_${tableNum}
     */
    private final String table;

    /**
     * the into columns
     * e.g. [ column_name, column_name ]
     */
    private final String[] columns;

    /**
     * the into values
     * e.g. [ #{property,jdbcType=JDBC_TYPE}, #{property} ]
     */
    private final String[] values;

    public SqlInsertBuilder(String table, String[] columns, String[] values) {
        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public SQL insertOne() {
        return new SQL() {
            {
                INSERT_INTO(getTable());
                INTO_COLUMNS(getColumns());
                INTO_VALUES(getValues());
            }
        };
    }

    public String getTable() {
        return table;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getValues() {
        return values;
    }

}
