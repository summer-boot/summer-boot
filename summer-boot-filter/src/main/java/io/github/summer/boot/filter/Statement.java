package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 命令和参数
 *
 * @author changebooks@qq.com
 */
public final class Statement implements Serializable {
    /**
     * SELECT FROM table WHERE column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     * INSERT INTO table (column, column) VALUES (:parameterName, :parameterName), (:parameterName, :parameterName), (:parameterName, :parameterName)
     * UPDATE table SET column = :parameterName, column = column + 1 WHERE column = :parameterName
     */
    private String sql;

    /**
     * [ the {@link Parameter} instance ]
     */
    private List<Parameter> parameters;

    public Statement() {
    }

    public Statement(String sql) {
        setSql(sql);
    }

    public Statement(String sql, List<Parameter> parameters) {
        setSql(sql);
        setParameters(parameters);
    }

    @Override
    public String toString() {
        String sql = getSql();
        List<Parameter> parameters = getParameters();
        return "{\"sql\": \"%s\", \"parameters\": %s}".formatted(sql, parameters);
    }

    public void join(Statement statement) {
        if (statement == null) {
            return;
        }

        String sql = statement.getSql();
        List<Parameter> parameters = statement.getParameters();

        joinSql(sql);
        joinParameters(parameters);
    }

    @NotNull
    public String getSql() {
        return sql != null ? sql : "";
    }

    public void setSql(String sql) {
        this.sql = sql != null ? sql : "";
    }

    public void joinSql(String sql) {
        if (sql == null || sql.isBlank()) {
            return;
        }

        if (this.sql == null || this.sql.isBlank()) {
            this.sql = sql;
        } else {
            this.sql = this.sql + " " + sql;
        }
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        if (parameters != null) {
            this.parameters = parameters.stream()
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            this.parameters = null;
        }
    }

    public void setParameter(Parameter parameter) {
        if (parameter != null) {
            this.parameters = Collections.singletonList(parameter);
        } else {
            this.parameters = null;
        }
    }

    public void joinParameters(List<Parameter> parameters) {
        if (parameters == null) {
            return;
        }

        if (this.parameters != null) {
            for (Parameter parameter : parameters) {
                if (parameter != null) {
                    this.parameters.add(parameter);
                }
            }
        } else {
            setParameters(parameters);
        }
    }

    public void joinParameter(Parameter parameter) {
        if (parameter == null) {
            return;
        }

        if (this.parameters != null) {
            this.parameters.add(parameter);
        } else {
            setParameter(parameter);
        }
    }

}
