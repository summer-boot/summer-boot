package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;

/**
 * 分组
 *
 * @author changebooks@qq.com
 */
public class Group2 implements Serializable {
    /**
     * GROUP BY name, name
     */
    private List<String> columns;

    /**
     * HAVING column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     */
    private String sql;

    /**
     * [ the {@link Parameter} instance ]
     */
    private List<Parameter> parameters;

    @Override
    public String toString() {
        List<String> columns = getColumns();
        String sql = getSql();
        List<Parameter> parameters = getParameters();
        return "{\"columns\": %s, \"sql\": \"%s\", \"parameters\": %s}".formatted(columns, sql, parameters);
    }

    @NotNull
    public List<String> getColumns() {
        return columns != null ? columns : new ArrayList<>();
    }

    public void setColumns(List<String> columns) {
        this.columns = Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .toList();
    }

    @NotNull
    public String getSql() {
        return sql != null ? sql : "";
    }

    public void setSql(String sql) {
        this.sql = sql != null ? sql.trim() : null;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

}
