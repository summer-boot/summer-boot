package io.github.summer.boot.database.sql;

import io.github.summer.boot.database.tag.TagResult;
import io.github.summer.boot.database.tag.TagResultFilter;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Build UPDATE SQL
 *
 * @param <T> the type of the POJO
 * @author changebooks@qq.com
 */
public class SqlUpdateBuilder<T> implements SqlBuilder.SqlUpdate<T> {
    /**
     * the table name
     * e.g. table_name, table_name_${tableNum}
     */
    private final String table;

    /**
     * the set columns
     * e.g. [ {@link TagResult}, {@link TagResult} ]
     */
    private final List<TagResult> columns;

    /**
     * the id condition
     * e.g. [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    private final String[] whereId;

    public SqlUpdateBuilder(String table, List<TagResult> columns, String[] whereId) {
        this.table = table;
        this.columns = columns;
        this.whereId = whereId;
    }

    @Override
    public SQL updateOne(T record, List<String> exclude) {
        List<TagResult> columns;
        try {
            columns = TagResultFilter.removeNull(getColumns(), record);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        columns = TagResultFilter.removeExclude(columns, exclude);
        String[] sets = SqlSegment.getSets(columns);

        return new SQL() {
            {
                UPDATE(getTable());
                SET(sets);
                WHERE(getWhereId());
            }
        };
    }

    public String getTable() {
        return table;
    }

    public List<TagResult> getColumns() {
        return columns;
    }

    public String[] getWhereId() {
        return whereId;
    }

}
