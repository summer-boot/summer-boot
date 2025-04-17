package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public final class StatementJoiner {

    private StatementJoiner() {
    }

    /**
     * Join Statement
     *
     * @param list [ the {@link Statement} instance ]
     * @return the {@link Statement} instance
     */
    @NotNull
    public static Statement join(@NotNull List<Statement> list) {
        String sql = joinSql(list);
        String logicalOperator = getLogicalOperator(list);
        List<Parameter> parameters = joinParameter(list);

        return new Statement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * Join Sql
     *
     * @param list [ the {@link Statement} instance ]
     * @return column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     */
    @NotNull
    public static String joinSql(@NotNull List<Statement> list) {
        String first = null;
        StringBuilder others = new StringBuilder();

        for (Statement statement : list) {
            if (statement == null) {
                continue;
            }

            String sql = statement.getSql();
            if (sql.isEmpty()) {
                continue;
            }

            if (first == null) {
                first = sql;
                continue;
            }

            String other = statement.prefixedSql();
            if (other.isEmpty()) {
                continue;
            }

            others.append(" ").append(other);
        }

        if (others.isEmpty()) {
            return first != null ? first : "";
        } else {
            return "(" + first + ")" + others;
        }
    }

    /**
     * Logical Operator
     *
     * @param list [ the {@link Statement} instance ]
     * @return AND, OR
     */
    @NotNull
    public static String getLogicalOperator(@NotNull List<Statement> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(Statement::getLogicalOperator)
                .orElse("");
    }

    /**
     * Join Parameter
     *
     * @param list [ the {@link Statement} instance ]
     * @return [ the {@link Parameter} instance ]
     */
    @NotNull
    public static List<Parameter> joinParameter(@NotNull List<Statement> list) {
        List<Parameter> result = new ArrayList<>();

        for (Statement statement : list) {
            if (statement == null) {
                continue;
            }

            List<Parameter> parameters = statement.getParameters();
            if (parameters == null) {
                continue;
            }

            for (Parameter parameter : parameters) {
                if (parameter == null) {
                    continue;
                }

                result.add(parameter);
            }
        }

        return result;
    }

}
