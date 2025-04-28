package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.SqlParameter;
import io.github.summer.boot.sql.Group;
import io.github.summer.boot.sql.GroupParser;
import io.github.summer.boot.sql.filter.FilterParserImpl;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class GroupParserImpl implements GroupParser {
    /**
     * the {@link FilterParser} instance
     */
    private final FilterParser filterParser;

    public GroupParserImpl() {
        this.filterParser = new FilterParserImpl();
    }

    @Override
    public SqlParameter parse(Group group) {
        if (group == null) {
            return null;
        }

        List<String> columns = group.getColumns();
        SqlParameter sqlParameter = parseGroup(columns);
        if (sqlParameter == null) {
            return null;
        }

        List<BaseFilter> filters = group.getFilters();
        joinHaving(sqlParameter, filters);
        return sqlParameter;
    }

    /**
     * 解析分组
     *
     * @param columns [ column ]
     * @return the {@link SqlParameter} instance
     */
    public SqlParameter parseGroup(List<String> columns) {
        if (columns == null) {
            return null;
        }

        String sql = String.join(", ", columns);
        if (sql.isEmpty()) {
            return null;
        } else {
            return new SqlParameter(sql, null);
        }
    }

    /**
     * 连接条件
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param filters      [ the {@link BaseFilter} instance ]
     */
    public void joinHaving(@NotNull SqlParameter sqlParameter, List<BaseFilter> filters) {
        if (filters == null) {
            return;
        }

        FilterParser filterParser = getFilterParser();

        SqlParameter parsedHaving = filterParser.parse(filters, "HAVING");
        sqlParameter.join(parsedHaving);
    }

    /**
     * 连接前缀
     *
     * @param sql name, name
     * @return GROUP BY name, name
     */
    public String prefixed(String sql) {
        if (sql == null) {
            return null;
        }

        if (sql.isBlank()) {
            return "";
        } else {
            return "GROUP BY " + sql;
        }
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
