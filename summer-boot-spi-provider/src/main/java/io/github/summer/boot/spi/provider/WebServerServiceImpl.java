package io.github.summer.boot.spi.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author changebooks@qq.com
 */
public class WebServerServiceImpl implements WebServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerServiceImpl.class);

    /**
     * 注册注销服务
     */
    private final WebServerManager webServerManager;

    /**
     * 已上线？
     */
    private volatile boolean online = false;

    public WebServerServiceImpl(WebServerManager webServerManager) {
        this.webServerManager = webServerManager;
    }

    @Override
    public boolean onWebInitialized(int webPort) {
        try {
            return webServerManager.onWebInitialized(webPort);
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, onWebInitialized failed, webPort: {}, throwable: ",
                    webPort, ex);
            return false;
        }
    }

    @Override
    public boolean register() {
        if (isRegister()) {
            return true;
        }

        try {
            return webServerManager.register();
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, register failed, throwable: ", ex);
            return false;
        }
    }

    @Override
    public boolean deregister() {
        if (isDeregister()) {
            return true;
        }

        try {
            return webServerManager.deregister();
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, deregister failed, throwable: ", ex);
            return false;
        }
    }

    @Override
    public boolean isRegister() {
        try {
            return webServerManager.isRegister();
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, isRegister failed, throwable: ", ex);
            return false;
        }
    }

    @Override
    public boolean isDeregister() {
        try {
            return webServerManager.isDeregister();
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, isDeregister failed, throwable: ", ex);
            return false;
        }
    }

    @Override
    public boolean online() {
        if (isOnline()) {
            return true;
        }

        online = true;
        return true;
    }

    @Override
    public boolean offline() {
        if (isOffline()) {
            return true;
        }

        online = false;
        return true;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    @Override
    public boolean isOffline() {
        return !online;
    }

    @Override
    public WebServerSchema newWebSchema() {
        try {
            return webServerManager.newWebSchema();
        } catch (Throwable ex) {
            LOGGER.error("WebServerServiceImpl failed, newWebSchema failed, throwable: ", ex);
            return null;
        }
    }

}
