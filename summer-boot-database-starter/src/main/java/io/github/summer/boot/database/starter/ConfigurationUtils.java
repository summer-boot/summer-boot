package io.github.summer.boot.database.starter;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Configuration Utils
 *
 * @author changebooks@qq.com
 */
public final class ConfigurationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtils.class);

    private ConfigurationUtils() {
    }

    /**
     * obtain Configuration instance from SqlSessionFactoryBean instance
     *
     * @param factoryBean the {@link SqlSessionFactoryBean} instance
     * @return Configuration instance
     */
    public static Configuration getConfiguration(SqlSessionFactoryBean factoryBean) {
        SqlSessionFactory factory = getSessionFactory(factoryBean);
        if (factory != null) {
            return factory.getConfiguration();
        } else {
            return null;
        }
    }

    /**
     * obtain SqlSessionFactory instance from SqlSessionFactoryBean instance
     *
     * @param factoryBean the {@link SqlSessionFactoryBean} instance
     * @return SqlSessionFactory instance
     */
    public static SqlSessionFactory getSessionFactory(SqlSessionFactoryBean factoryBean) {
        Assert.state(factoryBean != null, "factoryBean must not be null");

        try {
            return factoryBean.getObject();
        } catch (Exception ex) {
            LOGGER.error("SqlSessionFactoryBean.getObject throwable: ", ex);
            throw new RuntimeException(ex);
        }
    }

}
