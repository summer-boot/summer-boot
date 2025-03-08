package io.github.summer.boot.database.annotation;

import java.lang.annotation.*;

/**
 * &lt;resultMap id="id name" type="class"&gt;&lt;&#47;resultMap&gt;
 *
 * @author changebooks@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MybatisResultMap {
    /**
     * table name to java bean name
     *
     * @return the table name
     */
    String table() default "";

}
