package io.github.summer.boot.binlog;

import io.github.summer.boot.util.TypeCast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Binary log Utils
 *
 * @author changebooks@qq.com
 */
public final class BinlogUtils {
    /**
     * 匹配表名
     */
    private static final String TABLE_PATTERN = "*";

    /**
     * 属性名，主键
     */
    private static final String FIELD_NAME_ID = "id";

    private BinlogUtils() {
    }

    /**
     * 监控表名
     *
     * @param database  库名
     * @param tableList [ 表名 ]
     * @return [ 库名.表名, 库名.* ]
     */
    public static String[] tableList(String database, List<String> tableList) {
        if (tableList == null) {
            tableList = new ArrayList<>();
        }

        if (tableList.isEmpty()) {
            tableList.add(TABLE_PATTERN);
        }

        List<String> result = prefixedTables(database, tableList);
        if (result != null) {
            return result.toArray(new String[]{});
        } else {
            return null;
        }
    }

    /**
     * 库名 + 表名
     *
     * @param database  库名
     * @param tableList [ 表名 ]
     * @return [ 库名.表名 ]
     */
    public static List<String> prefixedTables(String database, List<String> tableList) {
        if (tableList == null) {
            return null;
        }

        return tableList
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(x -> prefixedTable(database, x))
                .filter(Objects::nonNull)
                .filter(x -> !x.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 库名 + 表名
     *
     * @param database 库名
     * @param table    表名
     * @return 库名.表名
     */
    public static String prefixedTable(String database, String table) {
        if (table == null || table.isEmpty()) {
            return table;
        }

        if (database == null || database.isEmpty()) {
            return table;
        } else {
            return database + "." + table;
        }
    }

    /**
     * 解析主键
     *
     * @param before 操作前记录，[ field_name : field_value ]
     * @param after  操作后记录，[ field_name : field_value ]
     * @return 主键
     */
    public static long parseId(Map<String, Object> before, Map<String, Object> after) {
        Object value = parseValue(FIELD_NAME_ID, before, after);
        if (value != null) {
            return TypeCast.toLong(value, 0L);
        } else {
            return 0L;
        }
    }

    /**
     * 解析值
     *
     * @param fieldName 字段名
     * @param before    操作前记录，[ field_name : field_value ]
     * @param after     操作后记录，[ field_name : field_value ]
     * @return 字段值
     */
    public static Object parseValue(String fieldName, Map<String, Object> before, Map<String, Object> after) {
        if (fieldName == null || fieldName.isEmpty()) {
            return null;
        }

        if (after != null) {
            Object value = after.get(fieldName);
            if (value != null) {
                return value;
            }
        }

        if (before != null) {
            return before.get(fieldName);
        } else {
            return null;
        }
    }

    /**
     * 拼接幂等
     *
     * @param position  日志位置
     * @param name      日志名称
     * @param id        操作主键
     * @param timestamp 操作时间|毫秒
     * @return 幂等
     */
    public static String joinIdempotent(String position, String name, long id, long timestamp) {
        return String.format(
                "%s_%s_%d_%d",
                (position != null ? position : ""),
                (name != null ? name : ""),
                id,
                timestamp
        );
    }

}
