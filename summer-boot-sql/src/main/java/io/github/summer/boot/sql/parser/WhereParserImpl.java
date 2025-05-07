package io.github.summer.boot.sql.parser;

import io.github.summer.boot.filter.BaseFilter;
import io.github.summer.boot.filter.Statement;
import io.github.summer.boot.filter.StatementParser;
import io.github.summer.boot.sql.WhereParser;
import io.github.summer.boot.sql.filter.FilterParserImpl;

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
        this.statementParser = new StatementParser(new FilterParserImpl());
    }

    @Override
    public Statement parse(List<BaseFilter> list) {
        StatementParser statementParser = getStatementParser();
        return statementParser.parse(list, "WHERE");
    }

    @Override
    public Statement parse(BaseFilter filter) {
        StatementParser statementParser = getStatementParser();
        return statementParser.parse(filter, "WHERE");
    }

    @Override
    public String prefixed(String sql) {
        if (sql == null || sql.isBlank()) {
            return "";
        } else {
            return "WHERE " + sql;
        }
    }

    public StatementParser getStatementParser() {
        return statementParser;
    }

}
