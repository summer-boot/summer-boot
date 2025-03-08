package io.github.summer.boot.xdatabase;

import io.github.summer.boot.value.Parameter;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令和参数
 *
 * @author changebooks@qq.com
 */
public final class SqlParameterParser {

    private SqlParameterParser() {
    }

    /**
     * 解析
     *
     * @param sql        命令
     * @param parameters 参数列表
     * @return the {@link SqlParameter} instance
     */
    @NotNull
    public static SqlParameter parse(String sql, List<Parameter> parameters) {
        SqlParameter sqlParameter = new SqlParameter();

        sqlParameter.setOriginalSql(sql);
        sqlParameter.setOriginalParameters(parameters);

        parseSql(sqlParameter);
        parseParameters(sqlParameter);

        return sqlParameter;
    }

    /**
     * 解析命令
     *
     * @param sqlParameter the {@link SqlParameter} instance
     */
    static void parseSql(@NotNull SqlParameter sqlParameter) {
        String originalSql = sqlParameter.getOriginalSql();
        if (originalSql == null) {
            return;
        }

        char[] statement = originalSql.toCharArray();
        if (statement.length == 0) {
            return;
        }

        final StringBuilder sqlBuilder = new StringBuilder();
        final List<String> parameterNames = new ArrayList<>();

        StringBuilder expression = null;
        int i = 0;
        while (i < statement.length) {
            if (statement[i] != ':') {
                sqlBuilder.append(statement[i]);
                i++;
                continue;
            }

            // statement[i] is ':'

            i++;

            // ':' is final character
            if (i == statement.length) {
                sqlBuilder.append(':');
                break;
            }

            // skip escape
            if (statement[i] == ':') {
                sqlBuilder.append(':');
                i++;
                continue;
            }

            if (expression == null) {
                expression = new StringBuilder();
            } else {
                expression.setLength(0);
            }

            while (i < statement.length) {
                if (isParameterNamePart(statement[i])) {
                    expression.append(statement[i]);
                    i++;
                } else {
                    break;
                }
            }

            if (expression.isEmpty()) {
                sqlBuilder.append(':');
            } else {
                sqlBuilder.append('?');
                parameterNames.add(expression.toString());
            }
        }

        String sql = sqlBuilder.toString();
        sqlParameter.setSql(sql);
        sqlParameter.setParameterNames(parameterNames);
    }

    /**
     * 解析参数
     *
     * @param sqlParameter the {@link SqlParameter} instance
     */
    static void parseParameters(@NotNull SqlParameter sqlParameter) {
        List<Parameter> originalParameters = sqlParameter.getOriginalParameters();
        if (originalParameters == null) {
            return;
        }

        Map<String, Value> parameters = new HashMap<>();

        for (Parameter originalParameter : originalParameters) {
            if (originalParameter == null) {
                continue;
            }

            String parameterName = originalParameter.getName();
            if (parameterName == null) {
                continue;
            }

            String name = parameterName.trim();
            Value value = originalParameter.getValue();

            parameters.put(name, value);
        }

        sqlParameter.setParameters(parameters);
    }

    /**
     * 符合参数名称规范？
     *
     * @param parameterNamePart the character of parameter name
     * @return True-Az09_$、False-"':&,;()|=+-*%/\<>^
     */
    static boolean isParameterNamePart(char parameterNamePart) {
        return Character.isJavaIdentifierPart(parameterNamePart);
    }

}
