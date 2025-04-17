package io.github.summer.boot.sql;

import io.github.summer.boot.value.Parameter;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 命令和参数
 *
 * @author changebooks@qq.com
 */
public final class SqlParameter implements Serializable {
    /**
     * SELECT FROM table WHERE column = :parameterName AND column IS NOT NULL OR (column >= :parameterName AND column <= :parameterName)
     * INSERT INTO table (column, column) VALUES (:parameterName, :parameterName), (:parameterName, :parameterName), (:parameterName, :parameterName)
     * UPDATE table SET column = :parameterName, column = column + 1 WHERE column = :parameterName
     */
    private String originalSql;

    /**
     * [ the {@link Parameter} instance ]
     */
    private List<Parameter> originalParameters;

    /**
     * SELECT FROM table WHERE column = ? AND column IS NOT NULL OR (column >= ? AND column <= ?)
     * INSERT INTO table (column, column) VALUES (?, ?), (?, ?), (?, ?)
     * UPDATE table SET column = ?, column = column + 1 WHERE column = ?
     */
    private String sql;

    /**
     * [ Parameter Name ]
     */
    private List<String> parameterNames;

    /**
     * [ Parameter Name : Parameter Value ]
     */
    private Map<String, Value> parameters;

    /**
     * [ [ Parameter Name : Parameter Value ] ]
     */
    private List<Map<String, Value>> parametersList;

    public String getOriginalSql() {
        return originalSql;
    }

    public void setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public List<Parameter> getOriginalParameters() {
        return originalParameters;
    }

    public void setOriginalParameters(List<Parameter> originalParameters) {
        this.originalParameters = originalParameters;
    }

    @NotNull
    public String getSql() {
        return sql != null ? sql : "";
    }

    public void setSql(String sql) {
        this.sql = sql != null ? sql.trim() : "";
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    public Map<String, Value> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Value> parameters) {
        this.parameters = parameters;
    }

    public List<Map<String, Value>> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<Map<String, Value>> parametersList) {
        this.parametersList = parametersList;
    }

}
