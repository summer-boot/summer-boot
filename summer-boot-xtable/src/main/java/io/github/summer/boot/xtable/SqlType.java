package io.github.summer.boot.xtable;

import io.github.summer.boot.value.ValueType;

import java.sql.Types;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sql Type mapping Value Type
 *
 * @author changebooks@qq.com
 */
public final class SqlType {

    public static final Map<Integer, Integer> STANDARD_MAPPING = new ConcurrentHashMap<>(64);

    static {
        STANDARD_MAPPING.put(Types.CHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.NCHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.VARCHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.NVARCHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.VARBINARY, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.LONGVARCHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.LONGNVARCHAR, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.LONGVARBINARY, ValueType.STRING.code);
        STANDARD_MAPPING.put(Types.BOOLEAN, ValueType.INTEGER.code);
        STANDARD_MAPPING.put(Types.TINYINT, ValueType.INTEGER.code);
        STANDARD_MAPPING.put(Types.SMALLINT, ValueType.INTEGER.code);
        STANDARD_MAPPING.put(Types.INTEGER, ValueType.INTEGER.code);
        STANDARD_MAPPING.put(Types.BIGINT, ValueType.LONG.code);
        STANDARD_MAPPING.put(Types.FLOAT, ValueType.BIG_DECIMAL.code);
        STANDARD_MAPPING.put(Types.REAL, ValueType.BIG_DECIMAL.code);
        STANDARD_MAPPING.put(Types.DOUBLE, ValueType.BIG_DECIMAL.code);
        STANDARD_MAPPING.put(Types.NUMERIC, ValueType.BIG_DECIMAL.code);
        STANDARD_MAPPING.put(Types.DECIMAL, ValueType.BIG_DECIMAL.code);
        STANDARD_MAPPING.put(Types.DATE, ValueType.DATE.code);
        STANDARD_MAPPING.put(Types.TIME, ValueType.DATE.code);
        STANDARD_MAPPING.put(Types.TIME_WITH_TIMEZONE, ValueType.DATE.code);
        STANDARD_MAPPING.put(Types.TIMESTAMP, ValueType.DATE.code);
        STANDARD_MAPPING.put(Types.TIMESTAMP_WITH_TIMEZONE, ValueType.DATE.code);
        STANDARD_MAPPING.put(Types.NULL, ValueType.OBJECT.code);
        STANDARD_MAPPING.put(Types.JAVA_OBJECT, ValueType.OBJECT.code);
        STANDARD_MAPPING.put(Types.DATALINK, ValueType.OBJECT.code);
    }

    private SqlType() {
    }

    public static int lookup(int sqlType) {
        return STANDARD_MAPPING.getOrDefault(sqlType, ValueType.OBJECT.code);
    }

}
