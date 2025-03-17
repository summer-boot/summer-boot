package io.github.summer.boot.sql.statement;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 条件
 *
 * @author changebooks@qq.com
 */
public final class Statement implements Serializable {
    /**
     * column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     */
    private String sql;

    /**
     * AND, OR
     */
    private String logicalOperator;

    /**
     * [ the {@link Parameter} instance ]
     */
    private List<Parameter> parameters;

    @Override
    public String toString() {
        String sql = getSql();
        String logicalOperator = getLogicalOperator();
        List<Parameter> parameters = getParameters();
        return "{\"sql\": \"%s\", \"logicalOperator\": \"%s\", \"parameters\": %s}".formatted(sql, logicalOperator, parameters);
    }

    @NotNull
    public String getSql() {
        return sql != null ? sql : "";
    }

    @NotNull
    public String getLogicalOperator() {
        return logicalOperator != null ? logicalOperator : "";
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Build Statement
     */
    public static final class Builder {
        /**
         * column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
         */
        private String sql;

        /**
         * AND, OR
         */
        private String logicalOperator;

        /**
         * [ the {@link Parameter} instance ]
         */
        private List<Parameter> parameters;

        public Statement build() {
            Statement result = new Statement();

            result.sql = sql;
            result.logicalOperator = logicalOperator;
            result.parameters = parameters;

            return result;
        }

        public Builder setSql(String sql) {
            this.sql = sql;
            return this;
        }

        public Builder setLogicalOperator(String logicalOperator) {
            this.logicalOperator = logicalOperator != null ? logicalOperator.trim() : null;
            return this;
        }

        public Builder setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder setParameter(Parameter parameter) {
            this.parameters = parameter != null ? Collections.singletonList(parameter) : null;
            return this;
        }

    }

}
