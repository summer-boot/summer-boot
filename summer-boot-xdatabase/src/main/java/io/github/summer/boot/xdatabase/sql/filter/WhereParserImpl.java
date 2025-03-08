package io.github.summer.boot.xdatabase.sql.filter;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Where;
import io.github.summer.boot.filter.WhereParser;
import io.github.summer.boot.value.Parameter;
import io.github.summer.boot.xdatabase.Preconditions;
import io.github.summer.boot.xdatabase.sql.statement.Statement;
import io.github.summer.boot.xdatabase.sql.statement.StatementParser;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class WhereParserImpl implements WhereParser {
    /**
     * the {@link StatementParser} instance
     */
    private final StatementParser statementParser;

    public WhereParserImpl() {
        this.statementParser = new StatementParser();
    }

    public WhereParserImpl(StatementParser statementParser) {
        Preconditions.requireNonNull(statementParser, "statementParser must not be null");

        this.statementParser = statementParser;
    }

    @Override
    public Where parseWhere(List<BaseFilter> filters) {
        StatementParser statementParser = getStatementParser();
        Statement statement = statementParser.parse(filters);
        return doParse(statement);
    }

    @Override
    public Where parseWhere(BaseFilter filter) {
        StatementParser statementParser = getStatementParser();
        Statement statement = statementParser.parse(filter);
        return doParse(statement);
    }

    @Override
    public String prefixedWhere(String sql) {
        if (sql == null) {
            return null;
        }

        String where = sql.trim();
        if (where.isEmpty()) {
            return "";
        } else {
            return "WHERE " + where;
        }
    }

    /**
     * Parse Statement
     *
     * @param statement the {@link Statement} instance
     * @return the {@link Where} instance
     */
    protected Where doParse(Statement statement) {
        if (statement == null) {
            return null;
        }

        Where result = new Where();

        String sql = statement.getSql();
        String where = prefixedWhere(sql);
        result.setSql(where);

        List<Parameter> parameters = statement.getParameters();
        result.setParameters(parameters);

        return result;
    }

    public StatementParser getStatementParser() {
        return statementParser;
    }

}
