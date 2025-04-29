package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 内部，命令和参数
 *
 * @author changebooks@qq.com
 */
final class InternalStatement implements Serializable {
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
    public String prefixedSql() {
        String sql = getSql();
        if (sql.isEmpty()) {
            return "";
        }

        String logicalOperator = getLogicalOperator();
        if (logicalOperator.isEmpty()) {
            return LogicalOperator.AND + " (" + sql + ")";
        } else {
            return logicalOperator + " (" + sql + ")";
        }
    }

    @NotNull
    public String getSql() {
        return sql != null ? sql : "";
    }

    public InternalStatement setSql(String sql) {
        this.sql = sql != null ? sql.trim() : "";
        return this;
    }

    @NotNull
    public String getLogicalOperator() {
        return logicalOperator != null ? logicalOperator : "";
    }

    public InternalStatement setLogicalOperator(String logicalOperator) {
        this.logicalOperator = logicalOperator != null ? logicalOperator.trim() : "";
        return this;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public InternalStatement setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public InternalStatement setParameter(Parameter parameter) {
        this.parameters = parameter != null ? Collections.singletonList(parameter) : null;
        return this;
    }

}
