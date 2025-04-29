package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 命令和参数
 *
 * @author changebooks@qq.com
 */
public final class StatementParser {
    /**
     * the {@link InternalStatementParser} instance
     */
    private final InternalStatementParser internalStatementParser;

    public StatementParser(@NotNull FilterParser filterParser) {
        this.internalStatementParser = new InternalStatementParser(filterParser);
    }

    /**
     * 解析列表
     *
     * @param list   [ the {@link BaseFilter} instance ]
     * @param prefix WHERE, HAVING
     * @return the {@link Statement} instance
     */
    public Statement parse(List<BaseFilter> list, String prefix) {
        InternalStatement internalStatement = internalStatementParser.parse(list);
        if (internalStatement == null) {
            return null;
        } else {
            return buildStatement(internalStatement, prefix);
        }
    }

    /**
     * 解析
     *
     * @param filter the {@link BaseFilter} instance
     * @param prefix WHERE, HAVING
     * @return the {@link Statement} instance
     */
    public Statement parse(BaseFilter filter, String prefix) {
        InternalStatement internalStatement = internalStatementParser.parse(filter);
        if (internalStatement == null) {
            return null;
        } else {
            return buildStatement(internalStatement, prefix);
        }
    }

    /**
     * InternalStatement to Statement
     *
     * @param internalStatement the {@link InternalStatement} instance
     * @param prefix            WHERE, HAVING
     * @return the {@link Statement} instance
     */
    @NotNull
    private Statement buildStatement(@NotNull InternalStatement internalStatement, String prefix) {
        String sql = internalStatement.getSql();
        String prefixedSql = prefixed(sql, prefix);
        List<Parameter> parameters = internalStatement.getParameters();
        return new Statement(prefixedSql, parameters);
    }

    /**
     * 连接前缀
     *
     * @param sql    column = ?
     * @param prefix WHERE, HAVING
     * @return WHERE column = ?, HAVING column = ?
     */
    @NotNull
    public String prefixed(String sql, String prefix) {
        if (sql == null || sql.isBlank()) {
            return "";
        }

        if (prefix == null || prefix.isBlank()) {
            return sql;
        } else {
            return prefix + " " + sql;
        }
    }

    @NotNull
    public FilterParser filterParser() {
        return internalStatementParser.filterParser();
    }

}
