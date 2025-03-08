package io.github.summer.boot.xdatabase;

import io.github.summer.boot.value.*;
import io.github.summer.boot.xdatabase.schema.TableReader;
import jakarta.validation.constraints.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 表概要
 *
 * @author changebooks@qq.com
 */
public final class SchemaReader {
    /**
     * the {@link TableReader} instance
     */
    private static TableReader tableReader = new TableReader();

    private SchemaReader() {
    }

    /**
     * Registry Schema
     *
     * @param connection the {@link Connection} instance
     * @param tableName  Table Name
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs
     *                      or
     *                      this method is called on a closed result set
     */
    public static void registry(@NotNull Connection connection, @NotNull String tableName) throws SQLException {
        String trimmedName = tableName.trim();
        Preconditions.requireNonEmpty(trimmedName, "tableName must not be empty");

        Schema tableSchema = SchemaReader.read(connection, trimmedName);
        Preconditions.requireNonNull(tableSchema, "unsupported tableName: " + trimmedName + ", dbName: " + connection.getCatalog());

        SchemaRegistry.put(trimmedName, tableSchema);
    }

    /**
     * Read Schema
     *
     * @param connection the {@link Connection} instance
     * @param tableName  Table Name
     * @return the {@link Schema} instance
     * @throws SQLException if the columnLabel is not valid;
     *                      if a database access error occurs
     *                      or
     *                      this method is called on a closed result set
     */
    public static Schema read(@NotNull Connection connection, @NotNull String tableName) throws SQLException {
        TableReader tableReader = getTableReader();

        Table table = tableReader.read(connection, tableName);
        if (table != null) {
            return read(table);
        } else {
            return null;
        }
    }

    /**
     * Read Schema
     *
     * @param table the {@link Table} instance
     * @return the {@link Schema} instance
     */
    @NotNull
    public static Schema read(@NotNull Table table) {
        Schema result = new Schema();

        setTableName(result, table);
        setIdName(result, table);
        setTableColumns(result, table);
        setAutoIncrementNames(result, table);
        setValueTypes(result, table);
        setDefaultValues(result, table);
        setColumnNames(result, table);
        setColumns(result, table);
        setColumnNamesOnInsert(result, table);
        setColumnsOnInsert(result, table);
        setValuesOnInsert(result, table);
        setDefaultCurrentDateOnInsert(result, table);
        setColumnNamesOnUpdate(result, table);
        setDefaultCurrentDateOnUpdate(result, table);

        return result;
    }

    /**
     * FROM table
     * INSERT INTO table
     * UPDATE table
     * DELETE FROM table
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setTableName(@NotNull Schema schema, @NotNull Table table) {
        String tableName = table.getName();
        schema.setTableName(tableName);
    }

    /**
     * Primary Key
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setIdName(@NotNull Schema schema, @NotNull Table table) {
        List<String> primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return;
        }

        String idName = primaryKey.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .findFirst()
                .orElse("");
        schema.setIdName(idName);
    }

    /**
     * [ TableColumn ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setTableColumns(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> columns = table.getColumns();
        if (columns == null) {
            return;
        }

        List<TableColumn> tableColumns = columns.stream()
                .filter(Objects::nonNull)
                .peek(x -> {
                    String columnName = x.getName();
                    String name = columnName != null ? columnName.trim() : "";
                    x.setName(name);
                })
                .toList();
        schema.setTableColumns(tableColumns);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setAutoIncrementNames(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Set<String> columnNames = tableColumns.stream()
                .filter(TableColumn::isAutoIncrement)
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toSet());
        schema.setAutoIncrementNames(columnNames);
    }

    /**
     * [ Column Name : Value Type ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setValueTypes(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Map<String, Integer> valueTypes = tableColumns.stream()
                .collect(
                        Collectors.toMap(
                                TableColumn::getName,
                                TableColumn::getValueType
                        )
                );
        schema.setValueTypes(valueTypes);
    }

    /**
     * [ Column Name : Default Value ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setDefaultValues(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Map<String, Value> defaultValues = tableColumns.stream()
                .collect(
                        Collectors.toMap(
                                TableColumn::getName,
                                column -> {
                                    int valueType = column.getValueType();
                                    return switch (valueType) {
                                        case Types.STRING -> new Value(column.getDefaultString());
                                        case Types.INTEGER -> new Value(column.getDefaultInteger());
                                        case Types.LONG -> new Value(column.getDefaultLong());
                                        case Types.BIG_DECIMAL -> new Value(column.getDefaultBigDecimal());
                                        case Types.DATE -> new Value(column.getDefaultDate());
                                        default -> throw new UnsupportedValueTypeException(valueType, column.getName());
                                    };
                                }
                        )
                );
        schema.setDefaultValues(defaultValues);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setColumnNames(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        List<String> columnNames = tableColumns.stream()
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .toList();
        schema.setColumnNames(columnNames);
    }

    /**
     * column, column
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setColumns(@NotNull Schema schema, @NotNull Table table) {
        List<String> columnNames = schema.getColumnNames();

        String columns = String.join(", ", columnNames);
        schema.setColumns(columns);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setColumnNamesOnInsert(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        List<String> columnNames = tableColumns.stream()
                .filter(x -> !x.isDefaultCurrentDateOnInsert())
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .toList();
        schema.setColumnNamesOnInsert(columnNames);
    }

    /**
     * column, column
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setColumnsOnInsert(@NotNull Schema schema, @NotNull Table table) {
        List<String> columnNames = schema.getColumnNamesOnInsert();

        String columns = String.join(", ", columnNames);
        schema.setColumnsOnInsert(columns);
    }

    /**
     * ?, ?
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setValuesOnInsert(@NotNull Schema schema, @NotNull Table table) {
        List<String> columnNames = schema.getColumnNamesOnInsert();

        String values = columnNames.stream()
                .map(x -> "?")
                .collect(Collectors.joining(", "));
        schema.setValuesOnInsert(values);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setDefaultCurrentDateOnInsert(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Set<String> columnNames = tableColumns.stream()
                .filter(TableColumn::isDefaultCurrentDateOnInsert)
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toSet());
        schema.setDefaultCurrentDateOnInsert(columnNames);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setColumnNamesOnUpdate(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Set<String> columnNames = tableColumns.stream()
                .filter(x -> !x.isDefaultCurrentDateOnUpdate())
                .filter(x -> !x.isDefaultCurrentDateOnInsert())
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toSet());
        schema.setColumnNamesOnUpdate(columnNames);
    }

    /**
     * [ Column Name ]
     *
     * @param schema the {@link Schema} instance
     * @param table  the {@link Table} instance
     */
    private static void setDefaultCurrentDateOnUpdate(@NotNull Schema schema, @NotNull Table table) {
        List<TableColumn> tableColumns = schema.getTableColumns();

        Set<String> columnNames = tableColumns.stream()
                .filter(TableColumn::isDefaultCurrentDateOnUpdate)
                .map(TableColumn::getName)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toSet());
        schema.setDefaultCurrentDateOnUpdate(columnNames);
    }

    @NotNull
    public static TableReader getTableReader() {
        return tableReader;
    }

    public static void setTableReader(@NotNull TableReader tableReader) {
        SchemaReader.tableReader = tableReader;
    }

}
