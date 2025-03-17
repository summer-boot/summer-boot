package io.github.summer.boot.xdatabase.value;

import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.value.Types;
import io.github.summer.boot.value.UnsupportedValueTypeException;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResultSet Get Value
 *
 * @author changebooks@qq.com
 */
public final class ValueGetter {

    private ValueGetter() {
    }

    /**
     * Get Values
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnNames [ Column Name ]
     * @param valueTypes  [ Column Name : Value Type ]
     * @return [ Column Name : Column Value ]
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    public static Map<String, Value> getValues(@NotNull ResultSet rs,
                                               @NotNull List<String> columnNames, @NotNull Map<String, Integer> valueTypes) throws SQLException {
        Map<String, Value> data = new HashMap<>(columnNames.size());
        if (columnNames.isEmpty()) {
            return data;
        }

        for (String columnName : columnNames) {
            if (columnName == null) {
                continue;
            }

            Integer valueType = valueTypes.get(columnName);
            Preconditions.requireNonNull(valueType, "valueType must not be null, columnName: " + columnName);

            Value value = getValue(rs, columnName, valueType);
            data.put(columnName, value);
        }

        return data;
    }

    /**
     * Get Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @param valueType  Value Type
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    public static Value getValue(@NotNull ResultSet rs, @NotNull String columnName, int valueType) throws SQLException {
        return switch (valueType) {
            case Types.STRING -> getString(rs, columnName);
            case Types.INTEGER -> getInteger(rs, columnName);
            case Types.LONG -> getLong(rs, columnName);
            case Types.BIG_DECIMAL -> getBigDecimal(rs, columnName);
            case Types.DATE -> getDate(rs, columnName);
            default -> throw new UnsupportedValueTypeException(valueType, columnName);
        };
    }

    /**
     * Get Value
     *
     * @param rs        the {@link ResultSet} instance
     * @param valueType Value Type
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    public static Value getValue(@NotNull ResultSet rs, int valueType) throws SQLException {
        return switch (valueType) {
            case Types.STRING -> getString(rs, 1);
            case Types.INTEGER -> getInteger(rs, 1);
            case Types.LONG -> getLong(rs, 1);
            case Types.BIG_DECIMAL -> getBigDecimal(rs, 1);
            case Types.DATE -> getDate(rs, 1);
            default -> throw new UnsupportedValueTypeException(valueType, "ColumnIndex[1]");
        };
    }

    /**
     * Get String Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getString(@NotNull ResultSet rs, @NotNull String columnName) throws SQLException {
        String valueString = rs.getString(columnName);
        return new Value(valueString);
    }

    /**
     * Get String Value
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnIndex Column Index
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getString(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        String valueString = rs.getString(columnIndex);
        return new Value(valueString);
    }

    /**
     * Get Integer Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getInteger(@NotNull ResultSet rs, @NotNull String columnName) throws SQLException {
        int valueInteger = rs.getInt(columnName);
        return new Value(valueInteger);
    }

    /**
     * Get Integer Value
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnIndex Column Index
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getInteger(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        int valueInteger = rs.getInt(columnIndex);
        return new Value(valueInteger);
    }

    /**
     * Get Long Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getLong(@NotNull ResultSet rs, @NotNull String columnName) throws SQLException {
        long valueLong = rs.getLong(columnName);
        return new Value(valueLong);
    }

    /**
     * Get Long Value
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnIndex Column Index
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getLong(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        long valueLong = rs.getLong(columnIndex);
        return new Value(valueLong);
    }

    /**
     * Get BigDecimal Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getBigDecimal(@NotNull ResultSet rs, @NotNull String columnName) throws SQLException {
        BigDecimal valueBigDecimal = rs.getBigDecimal(columnName);
        return new Value(valueBigDecimal);
    }

    /**
     * Get BigDecimal Value
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnIndex Column Index
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getBigDecimal(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal valueBigDecimal = rs.getBigDecimal(columnIndex);
        return new Value(valueBigDecimal);
    }

    /**
     * Get Date Value
     *
     * @param rs         the {@link ResultSet} instance
     * @param columnName Column Name
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getDate(@NotNull ResultSet rs, @NotNull String columnName) throws SQLException {
        Timestamp valueDate = rs.getTimestamp(columnName);
        return new Value(valueDate);
    }

    /**
     * Get Date Value
     *
     * @param rs          the {@link ResultSet} instance
     * @param columnIndex Column Index
     * @return Column Value
     * @throws SQLException if the columnName is not valid;
     *                      if a database access error occurs or this method is called on a closed result set
     */
    @NotNull
    private static Value getDate(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        Timestamp valueDate = rs.getTimestamp(columnIndex);
        return new Value(valueDate);
    }

}
