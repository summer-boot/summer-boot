package io.github.summer.boot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

/**
 * Application Context Holder Support
 *
 * @author changebooks@qq.com
 */
public class ApplicationContextHolderSupport implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextHolderSupport.class);

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        if (applicationContext != null) {
            LOGGER.info("ApplicationContextHolderSupport setApplicationContext, applicationContext is ok");

            ApplicationContextHolder.setApplicationContext(applicationContext);

            Environment environment = applicationContext.getEnvironment();
            ApplicationContextHolder.setEnvironment(environment);
        } else {
            LOGGER.warn("ApplicationContextHolderSupport setApplicationContext, applicationContext is null");
        }
    }

}
