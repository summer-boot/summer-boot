package io.github.summer.boot.binlog;

import io.debezium.data.Envelope;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Binary log Schema Parser
 *
 * @author changebooks@qq.com
 */
public final class BinlogParser {

    private BinlogParser() {
    }

    /**
     * 解析日志流
     *
     * @param record the {@link SourceRecord} instance
     * @return the {@link Binlog} instance
     */
    public static Binlog parse(SourceRecord record) {
        if (record == null) {
            return null;
        }

        Object value = record.value();
        if (value == null) {
            return null;
        }

        if (value instanceof Struct) {
            return parse((Struct) value);
        } else {
            return null;
        }
    }

    /**
     * 解析日志流
     *
     * @param value the {@link SourceRecord#value()} instance
     * @return the {@link Binlog} instance
     */
    public static Binlog parse(Struct value) {
        if (value == null) {
            return null;
        }

        Struct source = getStruct(value, Envelope.FieldName.SOURCE);
        String operation = getString(value, Envelope.FieldName.OPERATION);
        Long timestamp = getLong(value, Envelope.FieldName.TIMESTAMP);
        Struct before = getStruct(value, Envelope.FieldName.BEFORE);
        Struct after = getStruct(value, Envelope.FieldName.AFTER);

        Binlog result = new Binlog();

        String db = getString(source, "db");
        result.setDb(db);

        String table = getString(source, "table");
        result.setTable(table);

        Integer op = getOp(operation);
        result.setOp(op);

        result.setTimestamp(timestamp);

        String position = getPosition(source);
        result.setPosition(position);

        Map<String, Object> beforeMap = getMap(before);
        result.setBefore(beforeMap);

        Map<String, Object> afterMap = getMap(after);
        result.setAfter(afterMap);

        return result;
    }

    /**
     * 通过“字段名”，获取“字段值”
     *
     * @param value     the {@link Struct} instance
     * @param fieldName 字段名
     * @return 字段值，the {@link Struct} instance
     */
    public static Struct getStruct(Struct value, String fieldName) {
        if (value == null) {
            return null;
        }

        Object result = value.getWithoutDefault(fieldName);
        if (result == null) {
            return null;
        }

        if (result instanceof Struct) {
            return (Struct) result;
        } else {
            return null;
        }
    }

    /**
     * 通过“字段名”，获取“字段值”
     *
     * @param value     the {@link Struct} instance
     * @param fieldName 字段名
     * @return 字段值，the {@link Long} instance
     */
    public static Long getLong(Struct value, String fieldName) {
        if (value == null) {
            return null;
        }

        Object result = value.getWithoutDefault(fieldName);
        if (result == null) {
            return null;
        }

        if (result instanceof Long) {
            return (Long) result;
        } else {
            return null;
        }
    }

    /**
     * 通过“字段名”，获取“字段值”
     *
     * @param value     the {@link Struct} instance
     * @param fieldName 字段名
     * @return 字段值，the {@link String} instance
     */
    public static String getString(Struct value, String fieldName) {
        if (value == null) {
            return null;
        }

        Object result = value.getWithoutDefault(fieldName);
        if (result == null) {
            return null;
        }

        if (result instanceof String) {
            return (String) result;
        } else {
            return null;
        }
    }

    /**
     * Struct to Map
     *
     * @param value the {@link Struct} instance
     * @return the {@link Map} instance
     */
    public static Map<String, Object> getMap(Struct value) {
        if (value == null) {
            return null;
        }

        Schema schema = value.schema();
        if (schema == null) {
            return null;
        }

        List<Field> fields = schema.fields();
        if (fields == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>(fields.size());

        for (Field field : fields) {
            if (field == null) {
                continue;
            }

            String fieldName = field.name();
            if (fieldName == null) {
                continue;
            }

            Object fieldValue = value.getWithoutDefault(fieldName);
            result.put(fieldName, fieldValue);
        }

        return result;
    }

    /**
     * Envelope.Operation to BinlogOperation
     *
     * @param op the {@link Envelope.Operation} value
     * @return the {@link BinlogOperationCode} value
     */
    public static Integer getOp(String op) {
        if (op == null) {
            return null;
        }

        if (Envelope.Operation.READ.code().equals(op)) {
            return BinlogOperationCode.SELECT;
        }

        if (Envelope.Operation.CREATE.code().equals(op)) {
            return BinlogOperationCode.INSERT;
        }

        if (Envelope.Operation.UPDATE.code().equals(op)) {
            return BinlogOperationCode.UPDATE;
        }

        if (Envelope.Operation.DELETE.code().equals(op)) {
            return BinlogOperationCode.DELETE;
        }

        return null;
    }

    /**
     * 获取“日志位置”
     *
     * @param value the {@link Struct} instance
     * @return server_id + file + pos
     */
    public static String getPosition(Struct value) {
        Long serverId = getLong(value, "server_id");
        if (serverId == null) {
            serverId = 0L;
        }

        String file = getString(value, "file");
        if (file == null) {
            file = "";
        }

        Long pos = getLong(value, "pos");
        if (pos == null) {
            pos = 0L;
        }

        return String.format("%d_%s_%d", serverId, file, pos);
    }

}
