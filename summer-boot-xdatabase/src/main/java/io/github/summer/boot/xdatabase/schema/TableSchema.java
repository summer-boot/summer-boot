package io.github.summer.boot.xdatabase.schema;

import io.github.summer.boot.value.TableColumn;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;

/**
 * 表概要
 *
 * @author changebooks@qq.com
 */
public final class TableSchema implements Serializable {
    /**
     * FROM table
     * INSERT INTO table
     * UPDATE table
     * DELETE FROM table
     */
    private String tableName = "";

    /**
     * Primary Key
     */
    private String idName = "";

    /**
     * [ Column Name ]
     */
    private Set<String> autoIncrementNames = new HashSet<>();

    /**
     * [ Column Name : Value Type ]
     */
    private Map<String, Integer> valueTypes = new HashMap<>();

    /**
     * [ Column Name : Default Value ]
     */
    private Map<String, Value> defaultValues = new HashMap<>();

    /**
     * column, column
     */
    private String columns = "";

    /**
     * [ Column Name ]
     */
    private List<String> columnNames = new ArrayList<>();

    /**
     * column, column
     */
    private String columnsOnInsert = "";

    /**
     * ?, ?
     */
    private String valuesOnInsert = "";

    /**
     * [ Column Name ]
     */
    private List<String> columnNamesOnInsert = new ArrayList<>();

    /**
     * [ Column Name ]
     */
    private Set<String> defaultCurrentDateOnInsert = new HashSet<>();

    /**
     * [ Column Name ]
     */
    private Set<String> columnNamesOnUpdate = new HashSet<>();

    /**
     * [ Column Name ]
     */
    private Set<String> defaultCurrentDateOnUpdate = new HashSet<>();

    /**
     * [ TableColumn ]
     */
    private List<TableColumn> tableColumns;

    @NotNull
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName != null ? tableName.trim() : "";
    }

    @NotNull
    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName != null ? idName.trim() : "";
    }

    @NotNull
    public Set<String> getAutoIncrementNames() {
        return autoIncrementNames;
    }

    public void setAutoIncrementNames(Set<String> autoIncrementNames) {
        this.autoIncrementNames = autoIncrementNames != null ? autoIncrementNames : new HashSet<>();
    }

    @NotNull
    public Map<String, Integer> getValueTypes() {
        return valueTypes;
    }

    public void setValueTypes(Map<String, Integer> valueTypes) {
        this.valueTypes = valueTypes != null ? valueTypes : new HashMap<>();
    }

    @NotNull
    public Map<String, Value> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Map<String, Value> defaultValues) {
        this.defaultValues = defaultValues != null ? defaultValues : new HashMap<>();
    }

    @NotNull
    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns != null ? columns : "";
    }

    @NotNull
    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames != null ? columnNames : new ArrayList<>();
    }

    @NotNull
    public String getColumnsOnInsert() {
        return columnsOnInsert;
    }

    public void setColumnsOnInsert(String columnsOnInsert) {
        this.columnsOnInsert = columnsOnInsert != null ? columnsOnInsert : "";
    }

    @NotNull
    public String getValuesOnInsert() {
        return valuesOnInsert;
    }

    public void setValuesOnInsert(String valuesOnInsert) {
        this.valuesOnInsert = valuesOnInsert != null ? valuesOnInsert : "";
    }

    @NotNull
    public List<String> getColumnNamesOnInsert() {
        return columnNamesOnInsert;
    }

    public void setColumnNamesOnInsert(List<String> columnNamesOnInsert) {
        this.columnNamesOnInsert = columnNamesOnInsert != null ? columnNamesOnInsert : new ArrayList<>();
    }

    @NotNull
    public Set<String> getDefaultCurrentDateOnInsert() {
        return defaultCurrentDateOnInsert;
    }

    public void setDefaultCurrentDateOnInsert(Set<String> defaultCurrentDateOnInsert) {
        this.defaultCurrentDateOnInsert = defaultCurrentDateOnInsert != null ? defaultCurrentDateOnInsert : new HashSet<>();
    }

    @NotNull
    public Set<String> getColumnNamesOnUpdate() {
        return columnNamesOnUpdate;
    }

    public void setColumnNamesOnUpdate(Set<String> columnNamesOnUpdate) {
        this.columnNamesOnUpdate = columnNamesOnUpdate != null ? columnNamesOnUpdate : new HashSet<>();
    }

    @NotNull
    public Set<String> getDefaultCurrentDateOnUpdate() {
        return defaultCurrentDateOnUpdate;
    }

    public void setDefaultCurrentDateOnUpdate(Set<String> defaultCurrentDateOnUpdate) {
        this.defaultCurrentDateOnUpdate = defaultCurrentDateOnUpdate != null ? defaultCurrentDateOnUpdate : new HashSet<>();
    }

    @NotNull
    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns != null ? tableColumns : new ArrayList<>();
    }

}
