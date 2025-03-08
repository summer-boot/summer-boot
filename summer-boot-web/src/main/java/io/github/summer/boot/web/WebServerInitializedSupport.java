package io.github.summer.boot.web;

import io.github.summer.boot.base.Check;
import io.github.summer.boot.base.Result;
import io.github.summer.boot.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebServer Initialized
 *
 * @author changebooks@qq.com
 */
public abstract class WebServerInitializedSupport implements ApplicationListener<WebServerInitializedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerInitializedSupport.class);

    /**
     * Doing ?
     */
    private final AtomicBoolean executed = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        executeOnce();
    }

    /**
     * Execute Once
     */
    public synchronized void executeOnce() {
        LOGGER.info("WebServerInitializedSupport notice, executeOnce start");

        if (executed.get()) {
            LOGGER.warn("WebServerInitializedSupport warning, executeOnce doing");
            return;
        }

        if (executed.compareAndSet(false, true)) {
            LOGGER.info("WebServerInitializedSupport notice, executeOnce notice, execute start");

            execute();

            LOGGER.info("WebServerInitializedSupport notice, executeOnce notice, execute stop");
        } else {
            LOGGER.error("WebServerInitializedSupport failed, executeOnce failed, executed.compareAndSet failed");
        }

        LOGGER.info("WebServerInitializedSupport notice, executeOnce stop");
    }

    /**
     * Execute
     */
    public void execute() {
        LOGGER.info("WebServerInitializedSupport notice, execute notice, onExecute start");

        boolean continueOnline = onExecute();
        if (continueOnline) {
            LOGGER.info("WebServerInitializedSupport notice, execute notice, online continue");
            LOGGER.info("WebServerInitializedSupport notice, execute notice, online start");

            boolean isOnline = online();
            if (isOnline) {
                LOGGER.info("WebServerInitializedSupport notice, execute notice, online success");
            } else {
                LOGGER.error("WebServerInitializedSupport failed, execute failed, online failed");
            }

            LOGGER.info("WebServerInitializedSupport notice, execute notice, online stop");
        } else {
            LOGGER.warn("WebServerInitializedSupport warning, execute warning, online abort");
        }

        LOGGER.info("WebServerInitializedSupport notice, execute notice, onExecute stop");
    }

    /**
     * 初始化
     *
     * @return 上线？
     */
    public abstract boolean onExecute();

    /**
     * 上线
     *
     * @return 上线成功？
     */
    public boolean online() {
        Result<Boolean> result = healthSpi().online();
        if (ResultUtils.isSuccess(result)) {
            Boolean data = result.getData();
            return Check.isTrue(data);
        } else {
            return false;
        }
    }

    /**
     * 健康检查
     *
     * @return the {@link HealthSpi} instance
     */
    public abstract HealthSpi healthSpi();

}
