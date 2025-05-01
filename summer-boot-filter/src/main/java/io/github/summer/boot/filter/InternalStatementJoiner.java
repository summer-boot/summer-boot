package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 内部，命令和参数
 *
 * @author changebooks@qq.com
 */
final class InternalStatementJoiner {

    private InternalStatementJoiner() {
    }

    /**
     * Join Statement
     *
     * @param list [ the {@link InternalStatement} instance ]
     * @return the {@link InternalStatement} instance
     */
    @NotNull
    static InternalStatement join(@NotNull List<InternalStatement> list) {
        String sql = joinSql(list);
        String logicalOperator = getLogicalOperator(list);
        List<Parameter> parameters = joinParameter(list);

        return new InternalStatement()
                .setSql(sql)
                .setLogicalOperator(logicalOperator)
                .setParameters(parameters);
    }

    /**
     * Join Sql
     *
     * @param list [ the {@link InternalStatement} instance ]
     * @return column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     */
    @NotNull
    static String joinSql(@NotNull List<InternalStatement> list) {
        String first = null;
        StringBuilder others = new StringBuilder();

        for (InternalStatement statement : list) {
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
     * @param list [ the {@link InternalStatement} instance ]
     * @return AND, OR
     */
    @NotNull
    static String getLogicalOperator(@NotNull List<InternalStatement> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(InternalStatement::getLogicalOperator)
                .orElse("");
    }

    /**
     * Join Parameter
     *
     * @param list [ the {@link InternalStatement} instance ]
     * @return [ the {@link Parameter} instance ]
     */
    @NotNull
    static List<Parameter> joinParameter(@NotNull List<InternalStatement> list) {
        List<Parameter> result = new ArrayList<>();

        for (InternalStatement statement : list) {
            if (statement == null) {
                continue;
            }

            List<Parameter> parameters = statement.getParameters();
            if (parameters == null) {
                continue;
            }

            for (Parameter parameter : parameters) {
                if (parameter != null) {
                    result.add(parameter);
                }
            }
        }

        return result;
    }

}
