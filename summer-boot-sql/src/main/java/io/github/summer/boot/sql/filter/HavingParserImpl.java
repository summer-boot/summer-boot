package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Having;
import io.github.summer.boot.filter.HavingParser;
import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.statement.Statement;
import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class HavingParserImpl implements HavingParser {
    /**
     * the {@link StatementParser} instance
     */
    private final StatementParser statementParser;

    public HavingParserImpl() {
        this.statementParser = new StatementParser();
    }

    public HavingParserImpl(StatementParser statementParser) {
        Preconditions.requireNonNull(statementParser, "statementParser must not be null");

        this.statementParser = statementParser;
    }

    @Override
    public Having parseHaving(List<BaseFilter> filters) {
        StatementParser statementParser = getStatementParser();
        Statement statement = statementParser.parse(filters);
        return doParse(statement);
    }

    @Override
    public Having parseHaving(BaseFilter filter) {
        StatementParser statementParser = getStatementParser();
        Statement statement = statementParser.parse(filter);
        return doParse(statement);
    }

    @Override
    public String prefixedHaving(String sql) {
        if (sql == null) {
            return null;
        }

        String having = sql.trim();
        if (having.isEmpty()) {
            return "";
        } else {
            return "HAVING " + having;
        }
    }

    /**
     * Parse Statement
     *
     * @param statement the {@link Statement} instance
     * @return the {@link Having} instance
     */
    protected Having doParse(Statement statement) {
        if (statement == null) {
            return null;
        }

        Having result = new Having();

        String sql = statement.getSql();
        String having = prefixedHaving(sql);
        result.setSql(having);

        List<Parameter> parameters = statement.getParameters();
        result.setParameters(parameters);

        return result;
    }

    @NotNull
    public StatementParser getStatementParser() {
        return statementParser;
    }

}
