package io.github.summer.boot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Application Ready
 *
 * @author changebooks@qq.com
 */
public abstract class ApplicationReadySupport implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadySupport.class);

    /**
     * Doing ?
     */
    private final AtomicBoolean executed = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        executeOnce();
    }

    /**
     * Execute Once
     */
    public void executeOnce() {
        LOGGER.info("ApplicationReadySupport notice, executeOnce start");

        if (executed.get()) {
            LOGGER.warn("ApplicationReadySupport warning, executeOnce doing");
            return;
        }

        if (executed.compareAndSet(false, true)) {
            LOGGER.info("ApplicationReadySupport notice, executeOnce notice, execute start");

            execute();

            LOGGER.info("ApplicationReadySupport notice, executeOnce notice, execute stop");
        } else {
            LOGGER.error("ApplicationReadySupport failed, executeOnce failed, executed.compareAndSet failed");
        }

        LOGGER.info("ApplicationReadySupport notice, executeOnce stop");
    }

    /**
     * Execute
     */
    public void execute() {
        LOGGER.info("ApplicationReadySupport notice, execute notice, onExecute start");

        onExecute();

        LOGGER.info("ApplicationReadySupport notice, execute notice, onExecute stop");
    }

    /**
     * 准备
     */
    public abstract void onExecute();

}
