package io.github.summer.boot.database.tag;

import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Parse &#064;MybatisResult annotation's result
 *
 * @author changebooks@qq.com
 */
public final class TagResult implements Serializable {
    /**
     * &lt;result column="jdbc column" &#47;&gt;
     */
    private String column;

    /**
     * &lt;result jdbcType="jdbc type" &#47;&gt;
     */
    private JdbcType jdbcType;

    /**
     * &lt;result property="java field" &#47;&gt;
     */
    private String property;

    /**
     * java type
     */
    private Class<?> javaType;

    /**
     * &lt;id column="jdbc column" &#47;&gt; ?
     */
    private boolean id;

    /**
     * auto increment ?
     */
    private boolean autoIncrement;

    /**
     * java field
     */
    private Field field;

    @Override
    public String toString() {
        String column = Optional.ofNullable(getColumn()).orElse("");
        String jdbcType = Optional.ofNullable(getJdbcType()).map(JdbcType::name).orElse("");
        String property = Optional.ofNullable(getProperty()).orElse("");
        String javaType = Optional.ofNullable(getJavaType()).map(Class::getName).orElse("");
        boolean id = isId();
        boolean autoIncrement = isAutoIncrement();
        String field = Optional.ofNullable(getField()).map(Field::getName).orElse("");

        return "{" +
                "\"column\": \"" + column + "\", " +
                "\"jdbcType\": \"" + jdbcType + "\", " +
                "\"property\": \"" + property + "\", " +
                "\"javaType\": \"" + javaType + "\", " +
                "\"id\": " + id + ", " +
                "\"autoIncrement\": " + autoIncrement + ", " +
                "\"field\": \"" + field + "\"" +
                "}";
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
