package io.github.summer.boot.database.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable mybatis-starter
 *
 * @author changebooks@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(MybatisProviderConfiguration.class)
public @interface EnableMybatisProvider {
}
