package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.filter.StatementParser;
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
     * the {@link StatementParser} instance
     */
    private final StatementParser statementParser;

    public GroupParserImpl() {
        this.statementParser = new StatementParser(new FilterParserImpl());
    }

    @Override
    public Statement parse(Group group) {
        if (group == null) {
            return null;
        }

        Statement statement = parseGroup(group);
        if (statement == null) {
            return null;
        } else {
            Statement having = parseHaving(group);
            statement.join(having);
            return statement;
        }
    }

    /**
     * 解析分组
     *
     * @param group the {@link Group} instance
     * @return the {@link Statement} instance
     */
    public Statement parseGroup(@NotNull Group group) {
        List<String> columns = group.getColumns();
        return parseGroup(columns);
    }

    /**
     * 解析分组
     *
     * @param columns GROUP BY name, name
     * @return the {@link Statement} instance
     */
    public Statement parseGroup(List<String> columns) {
        if (columns == null) {
            return null;
        }

        String sql = String.join(", ", columns);
        if (sql.isBlank()) {
            return null;
        } else {
            return new Statement(sql, null);
        }
    }

    /**
     * 解析条件
     *
     * @param group the {@link Group} instance
     * @return the {@link Statement} instance
     */
    public Statement parseHaving(@NotNull Group group) {
        List<BaseFilter> filters = group.getFilters();
        return parseHaving(filters);
    }

    /**
     * 解析条件
     *
     * @param filters [ the {@link BaseFilter} instance ]
     * @return the {@link Statement} instance
     */
    public Statement parseHaving(List<BaseFilter> filters) {
        StatementParser statementParser = getStatementParser();
        return statementParser.parse(filters, "HAVING");
    }

    public StatementParser getStatementParser() {
        return statementParser;
    }

}
