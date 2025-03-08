package io.github.summer.boot.database.sql;

import io.github.summer.boot.database.tag.TagResult;
import org.apache.ibatis.type.JdbcType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Build SQL segment
 *
 * @author changebooks@qq.com
 */
public final class SqlSegment {
    /**
     * constraint lock SET column = column + 1
     */
    private static final String LOCK_SET = "%s = %s + 1";

    private SqlSegment() {
    }

    /**
     * COLUMN NAMES
     *
     * @param columns the {@link TagResult} list
     * @return [ column, column ]
     */
    public static String[] getColumns(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(TagResult::getColumn)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * WHERE CONDITIONS
     *
     * @param columns the {@link TagResult} list
     * @return [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    public static String[] getWhere(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(SqlSegment::joinKeyValue)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * WHERE CONDITIONS
     *
     * @param columns     the {@link TagResult} list
     * @param placeholder {@code #} if true; {@code $} if false
     * @return [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property}, column = ${property,jdbcType=JDBC_TYPE}, column = ${property} ]
     */
    public static String[] getWhere(List<TagResult> columns, boolean placeholder) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(x -> joinKeyValue(x, placeholder))
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * INSERT INTO COLUMNS
     *
     * @param columns the {@link TagResult} list
     * @return [ column, column ]
     */
    public static String[] getIntoColumns(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(TagResult::getColumn)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * INSERT INTO VALUES
     *
     * @param columns the {@link TagResult} list
     * @return [ #{property,jdbcType=JDBC_TYPE}, #{property} ]
     */
    public static String[] getIntoValues(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(SqlSegment::joinValue)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * UPDATE SETS
     *
     * @param columns the {@link TagResult} list
     * @return [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    public static String[] getSets(List<TagResult> columns) {
        return Optional.ofNullable(columns)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .map(SqlSegment::joinKeyValue)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
    }

    /**
     * constraint lock set
     *
     * @param tagResult the {@link TagResult} instance
     * @return column = column + 1
     */
    public static String getLockSet(TagResult tagResult) {
        if (tagResult == null) {
            return null;
        }

        String column = tagResult.getColumn();
        if (column == null) {
            return null;
        } else {
            return String.format(LOCK_SET, column, column);
        }
    }

    /**
     * join key = value
     *
     * <pre>
     * column = #{property,jdbcType=JDBC_TYPE}
     * column = #{property}
     * </pre>
     *
     * @param tagResult the {@link TagResult} instance
     * @return key = #{}
     */
    public static String joinKeyValue(TagResult tagResult) {
        if (tagResult == null) {
            return null;
        }

        String column = tagResult.getColumn();
        if (column == null) {
            return null;
        }

        String value = joinValue(tagResult);
        if (value != null) {
            return String.format("%s = %s", column, value);
        } else {
            return null;
        }
    }

    /**
     * join key = value
     *
     * <pre>
     * column = #{property,jdbcType=JDBC_TYPE}
     * column = #{property}
     * column = ${property,jdbcType=JDBC_TYPE}
     * column = ${property}
     * </pre>
     *
     * @param tagResult   the {@link TagResult} instance
     * @param placeholder {@code #} if true; {@code $} if false
     * @return key = #{}; key = ${}
     */
    public static String joinKeyValue(TagResult tagResult, boolean placeholder) {
        if (tagResult == null) {
            return null;
        }

        String column = tagResult.getColumn();
        if (column == null) {
            return null;
        }

        String value = joinValue(tagResult, placeholder);
        if (value != null) {
            return String.format("%s = %s", column, value);
        } else {
            return null;
        }
    }

    /**
     * join value
     *
     * <pre>
     * #{property,jdbcType=JDBC_TYPE}
     * #{property}
     * </pre>
     *
     * @param tagResult the {@link TagResult} instance
     * @return #{}
     */
    public static String joinValue(TagResult tagResult) {
        return joinValue(tagResult, true);
    }

    /**
     * join value
     *
     * <pre>
     * #{property,jdbcType=JDBC_TYPE}
     * #{property}
     * ${property,jdbcType=JDBC_TYPE}
     * ${property}
     * </pre>
     *
     * @param tagResult   the {@link TagResult} instance
     * @param placeholder {@code #} if true; {@code $} if false
     * @return #{}; ${}
     */
    public static String joinValue(TagResult tagResult, boolean placeholder) {
        if (tagResult == null) {
            return null;
        }

        String property = tagResult.getProperty();
        if (property == null) {
            return null;
        }

        JdbcType jdbcType = tagResult.getJdbcType();
        if (jdbcType != null) {
            return String.format("%s{%s,jdbcType=%s}", (placeholder ? "#" : "$"), property, jdbcType.name());
        } else {
            return String.format("%s{%s}", (placeholder ? "#" : "$"), property);
        }
    }

}
