package io.github.summer.boot.database.tag;

import io.github.summer.boot.database.annotation.MybatisResult;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;

/**
 * Parse &#064;MybatisResult annotation to TagResult class
 *
 * @author changebooks@qq.com
 */
public final class TagResultParser {

    private TagResultParser() {
    }

    /**
     * java bean field to {@link TagResult} class
     *
     * @param field java bean field
     * @return TagResult
     */
    public static TagResult parse(Field field) {
        if (field == null) {
            return null;
        }

        MybatisResult annotation = field.getAnnotation(MybatisResult.class);
        if (annotation == null) {
            return null;
        }

        String column = annotation.column();
        JdbcType jdbcType = annotation.jdbcType();
        String property = field.getName();
        Class<?> javaType = field.getType();
        boolean id = annotation.id();
        boolean autoIncrement = annotation.autoIncrement();

        TagResult result = new TagResult();

        result.setColumn(column);
        result.setJdbcType(jdbcType);
        result.setProperty(property);
        result.setJavaType(javaType);
        result.setId(id);
        result.setAutoIncrement(autoIncrement);
        result.setField(field);

        return result;
    }

    /**
     * set accessible true
     *
     * @param field java bean reflect field
     */
    public static void setAccessibleTrue(Field field) {
        if (field != null) {
            field.setAccessible(true);
        }
    }

}
