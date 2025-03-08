package io.github.summer.boot.xdatabase.value;

import io.github.summer.boot.value.Types;
import io.github.summer.boot.value.UnsupportedValueTypeException;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.Preconditions;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Prepared Statement Set Value
 *
 * @author changebooks@qq.com
 */
public final class ValueSetter {

    private ValueSetter() {
    }

    /**
     * Set Values
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterNames [ Parameter Name ]
     * @param list           [ [ Parameter Name : Parameter Value ] ]
     * @return Next Index
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    public static int setValues(@NotNull PreparedStatement ps,
                                @NotNull List<String> parameterNames, List<Map<String, Value>> list) throws SQLException {
        return setValues(ps, 1, parameterNames, list);
    }

    /**
     * Set Values
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param parameterNames [ Parameter Name ]
     * @param list           [ [ Parameter Name : Parameter Value ] ]
     * @return Next Index
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    public static int setValues(@NotNull PreparedStatement ps, int parameterIndex,
                                @NotNull List<String> parameterNames, List<Map<String, Value>> list) throws SQLException {
        if (parameterNames.isEmpty()) {
            return parameterIndex;
        }

        if (list == null) {
            return parameterIndex;
        }

        for (Map<String, Value> parameters : list) {
            parameterIndex = ValueSetter.setValues(ps, parameterIndex, parameterNames, parameters);
        }

        return parameterIndex;
    }

    /**
     * Set Values
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterNames [ Parameter Name ]
     * @param values         [ Parameter Name : Parameter Value ]
     * @return Next Index
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    public static int setValues(@NotNull PreparedStatement ps,
                                @NotNull List<String> parameterNames, Map<String, Value> values) throws SQLException {
        return setValues(ps, 1, parameterNames, values);
    }

    /**
     * Set Values
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param parameterNames [ Parameter Name ]
     * @param values         [ Parameter Name : Parameter Value ]
     * @return Next Index
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    public static int setValues(@NotNull PreparedStatement ps, int parameterIndex,
                                @NotNull List<String> parameterNames, Map<String, Value> values) throws SQLException {
        if (parameterNames.isEmpty()) {
            return parameterIndex;
        }

        if (values == null) {
            return parameterIndex;
        }

        for (String parameterName : parameterNames) {
            Preconditions.requireNonNull(parameterName, "parameterName must not be null");

            Value value = values.get(parameterName);
            Preconditions.requireNonNull(value, "value must not be null, parameterIndex: " + parameterIndex + ", parameterName: " + parameterName);

            setValue(ps, parameterIndex, value);
            parameterIndex++;
        }

        return parameterIndex;
    }

    /**
     * Set Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    public static void setValue(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        int valueType = value.getType();
        switch (valueType) {
            case Types.STRING -> setString(ps, parameterIndex, value);
            case Types.INTEGER -> setInteger(ps, parameterIndex, value);
            case Types.LONG -> setLong(ps, parameterIndex, value);
            case Types.BIG_DECIMAL -> setBigDecimal(ps, parameterIndex, value);
            case Types.DATE -> setDate(ps, parameterIndex, value);
            default -> throw new UnsupportedValueTypeException(valueType, "ParameterIndex[" + parameterIndex + "]");
        }
    }

    /**
     * Set String Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    private static void setString(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        String valueString = value.getValueString();
        ps.setString(parameterIndex, valueString);
    }

    /**
     * Set Integer Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    private static void setInteger(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        Integer valueInteger = value.getValueInteger();
        ps.setInt(parameterIndex, valueInteger);
    }

    /**
     * Set Long Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    private static void setLong(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        Long valueLong = value.getValueLong();
        ps.setLong(parameterIndex, valueLong);
    }

    /**
     * Set BigDecimal Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    private static void setBigDecimal(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        BigDecimal valueBigDecimal = value.getValueBigDecimal();
        ps.setBigDecimal(parameterIndex, valueBigDecimal);
    }

    /**
     * Set Date Value
     *
     * @param ps             the {@link PreparedStatement} instance
     * @param parameterIndex Parameter Index
     * @param value          Parameter Value
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
     *                      if a database access error occurs or this method is called on a closed prepared statement
     */
    private static void setDate(@NotNull PreparedStatement ps, int parameterIndex, @NotNull Value value) throws SQLException {
        Date valueDate = value.getValueDate();
        Timestamp valueTimestamp = valueDate != null ? new Timestamp(valueDate.getTime()) : null;
        ps.setTimestamp(parameterIndex, valueTimestamp);
    }

}
