package io.github.summer.boot.web;

import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Application Context Holder
 *
 * @author changebooks@qq.com
 */
public final class ApplicationContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextHolder.class);

    /**
     * hold the {@link ApplicationContext} instance
     */
    @Nullable
    private static ApplicationContext applicationContext;

    /**
     * hold the {@link Environment} instance
     */
    @Nullable
    private static Environment environment;

    private ApplicationContextHolder() {
    }

    /**
     * 获取Bean实例
     *
     * @param name         名称
     * @param requiredType 匹配类型，接口、父类
     * @param <T>          类型
     * @return Bean instance
     * @throws BeansException 创建实例失败
     */
    @Nullable
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        ApplicationContext applicationContext = getApplicationContext();

        if (applicationContext != null) {
            return applicationContext.getBean(name, requiredType);
        } else {
            LOGGER.warn("ApplicationContextHolder getBean, applicationContext is null, name: {}, requiredType: {}",
                    name, requiredType);
            return null;
        }
    }

    /**
     * 获取Bean实例
     *
     * @param requiredType 匹配类型，接口、父类
     * @param <T>          类型
     * @return Bean instance
     * @throws BeansException 创建实例失败
     */
    @Nullable
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        ApplicationContext applicationContext = getApplicationContext();

        if (applicationContext != null) {
            return applicationContext.getBean(requiredType);
        } else {
            LOGGER.warn("ApplicationContextHolder getBean, applicationContext is null, requiredType: {}", requiredType);
            return null;
        }
    }

    /**
     * 获取一个配置
     *
     * @param key 配置键
     * @return 配置值
     */
    @Nullable
    public static String getProperty(String key) {
        Environment environment = getEnvironment();

        if (environment != null) {
            return environment.getProperty(key);
        } else {
            LOGGER.warn("ApplicationContextHolder getProperty, environment is null, key: {}", key);
            return null;
        }
    }

    @Nullable
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        if (applicationContext != null) {
            ApplicationContextHolder.applicationContext = applicationContext;
        }
    }

    @Nullable
    public static Environment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(@Nullable Environment environment) {
        if (environment != null) {
            ApplicationContextHolder.environment = environment;
        }
    }

}
