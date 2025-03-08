package io.github.summer.boot.database.annotation;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.*;

/**
 * &lt;result column="jdbc column" jdbcType="jdbc type" property="java field" &#47;&gt;
 * &lt;id column="jdbc column" jdbcType="jdbc type" property="java field" &#47;&gt;
 *
 * @author changebooks@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MybatisResult {
    /**
     * &lt;result column="jdbc column" &#47;&gt;
     *
     * @return the column name
     */
    String column() default "";

    /**
     * &lt;result jdbcType="jdbc type" &#47;&gt;
     *
     * @return the jdbc type
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;

    /**
     * &lt;id column="jdbc column" &#47;&gt; ?
     *
     * @return {@code true} if id column; {@code false} if otherwise
     */
    boolean id() default false;

    /**
     * auto increment ?
     *
     * @return {@code true} if auto increment column; {@code false} if otherwise
     */
    boolean autoIncrement() default false;

}
