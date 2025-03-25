package io.github.summer.boot.filter;

import io.github.summer.boot.value.Parameter;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * 分组条件
 *
 * @author changebooks@qq.com
 */
public final class Having implements Serializable {
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
        String sql = getSql();
        List<Parameter> parameters = getParameters();
        return "{\"sql\": \"%s\", \"parameters\": %s}".formatted(sql, parameters);
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
