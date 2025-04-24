package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.FilterParser;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.sql.Group;
import io.github.summer.boot.sql.GroupParser;
import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.value.Parameter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return null;
    }

    /**
     * 解析列表
     *
     * @param columns [ Column Name ]
     * @return GROUP BY name, name
     */
    public String parseGroup(List<String> columns) {
        return "GROUP BY " +
                Optional.ofNullable(columns)
                        .orElse(Collections.emptyList())
                        .stream()
                        .filter(Objects::nonNull)
                        .map(String::trim)
                        .filter(x -> !x.isEmpty())
                        .collect(Collectors.joining(", "));
    }

    /**
     * 解析列表
     *
     * @param list [ the {@link BaseFilter} instance ]
     * @return the {@link SqlParameter} instance
     */
    public SqlParameter parseHaving(List<BaseFilter> list) {
        FilterParser filterParser = getFilterParser();
        Statement statement = filterParser.parse(list);
        return parseHaving(statement);
    }

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @return the {@link SqlParameter} instance
     */
    public SqlParameter parseHaving(BaseFilter filter) {
        FilterParser filterParser = getFilterParser();
        Statement statement = filterParser.parse(filter);
        return parseHaving(statement);
    }

    /**
     * Prefixed Sql
     *
     * @param statement the {@link Statement} instance
     * @return the {@link SqlParameter} instance
     */
    public SqlParameter parseHaving(Statement statement) {
        if (statement == null) {
            return null;
        }

        String sql = statement.getSql();
        String prefixedSql = prefixedHaving(sql);
        List<Parameter> parameters = statement.getParameters();

        SqlParameter result = new SqlParameter();

        result.setSql(prefixedSql);
        result.setParameters(parameters);

        return result;
    }

    /**
     * Prefixed Having
     *
     * @param sql column = ?
     * @return HAVING column = ?
     */
    public String prefixedHaving(String sql) {
        if (sql == null) {
            return null;
        }

        if (sql.isBlank()) {
            return "";
        } else {
            return "HAVING " + sql;
        }
    }

    public FilterParser getFilterParser() {
        return filterParser;
    }

}
