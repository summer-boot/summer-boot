package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.sql.WhereParser;
import io.github.summer.boot.value.Parameter;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class WhereParserImpl implements WhereParser {
    /**
     * the {@link FilterParser} instance
     */
    private final FilterParser filterParser;

    public WhereParserImpl() {
        this.filterParser = new FilterParserImpl();
    }

    @Override
    public SqlParameter parse(List<BaseFilter> list) {
        FilterParser filterParser = getFilterParser();
        Statement statement = filterParser.parse(list);
        return parse(statement);
    }

    @Override
    public SqlParameter parse(BaseFilter filter) {
        FilterParser filterParser = getFilterParser();
        Statement statement = filterParser.parse(filter);
        return parse(statement);
    }

    /**
     * Prefixed Sql
     *
     * @param statement the {@link Statement} instance
     * @return the {@link SqlParameter} instance
     */
    public SqlParameter parse(Statement statement) {
        if (statement == null) {
            return null;
        }

        String sql = statement.getSql();
        String prefixedSql = prefixedWhere(sql);
        List<Parameter> parameters = statement.getParameters();

        SqlParameter result = new SqlParameter();

        result.setSql(prefixedSql);
        result.setParameters(parameters);

        return result;
    }

    @Override
    public String prefixedWhere(String sql) {
        if (sql == null) {
            return null;
        }

        if (sql.isBlank()) {
            return "";
        } else {
            return "WHERE " + sql;
        }
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
