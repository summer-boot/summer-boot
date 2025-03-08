package io.github.summer.boot.database.tag;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Filter TagResult list
 *
 * @author changebooks@qq.com
 */
public final class TagResultFilter {

    private TagResultFilter() {
    }

    /**
     * filter Id from {@link TagResult} list
     *
     * @param columns the {@link TagResult} list
     * @return TagResult List With Id
     */
    public static List<TagResult> filterId(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(TagResult::isId)
                .collect(Collectors.toList());
    }

    /**
     * remove Id from {@link TagResult} list
     *
     * @param columns the {@link TagResult} list
     * @return TagResult List Without Id
     */
    public static List<TagResult> removeId(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> !x.isId())
                .collect(Collectors.toList());
    }

    /**
     * filter Auto Increment from {@link TagResult} list
     *
     * @param columns the {@link TagResult} list
     * @return TagResult List With Auto Increment
     */
    public static List<TagResult> filterAutoIncrement(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(TagResult::isAutoIncrement)
                .collect(Collectors.toList());
    }

    /**
     * remove Auto Increment from {@link TagResult} list
     *
     * @param columns the {@link TagResult} list
     * @return TagResult List Without Auto Increment
     */
    public static List<TagResult> removeAutoIncrement(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> !x.isAutoIncrement())
                .collect(Collectors.toList());
    }

    /**
     * remove Null from {@link TagResult} list
     *
     * @param columns the {@link TagResult} list
     * @param record  the POJO
     * @param <T>     the type of the POJO
     * @return TagResult List Without Null
     * @throws IllegalAccessException if this {@code Field} object is enforcing Java language access control
     *                                and
     *                                the underlying field is inaccessible.
     */
    public static <T> List<TagResult> removeNull(List<TagResult> columns, T record) throws IllegalAccessException {
        Objects.requireNonNull(record, "record must not be null");

        if (columns == null) {
            return null;
        }

        List<TagResult> result = new ArrayList<>();

        for (TagResult column : columns) {
            if (column == null) {
                continue;
            }

            Field field = column.getField();
            Objects.requireNonNull(field, "field must not be null");

            Object value = field.get(record);
            if (value != null) {
                result.add(column);
            }
        }

        return result;
    }

    /**
     * remove Exclude from Exclude column name list
     *
     * @param columns the {@link TagResult} list
     * @param exclude the Exclude column name list
     * @return TagResult List Without Exclude column
     */
    public static List<TagResult> removeExclude(List<TagResult> columns, List<String> exclude) {
        if (exclude == null) {
            return columns;
        }

        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> !exclude.contains(x.getColumn()))
                .collect(Collectors.toList());
    }

    /**
     * filter by Column name from {@link TagResult} list
     *
     * @param columns    the {@link TagResult} list
     * @param columnName the column name
     * @return First TagResult With the Column Name
     */
    public static TagResult filterColumnName(List<TagResult> columns, String columnName) {
        if (columnName == null) {
            return null;
        }

        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(x -> columnName.equals(x.getColumn()))
                .findFirst()
                .orElse(null);
    }

}
