package io.github.summer.boot.spi.provider;

import io.github.summer.boot.autoconfigure.WarmUpProperties;
import io.github.summer.boot.util.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 预热基类
 *
 * @author changebooks@qq.com
 */
public abstract class AbstractWebServerWarmUpChannelHandler extends AbstractWebServerChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebServerWarmUpChannelHandler.class);

    /**
     * 预热配置
     */
    private final WarmUpProperties properties;

    public AbstractWebServerWarmUpChannelHandler(WarmUpProperties properties, WebServerChannelHandler nextHandler) {
        super(nextHandler);
        this.properties = properties;
    }

    @Override
    public boolean doHandle() {
        String handlerName = getHandlerName();

        String url = getUrl();
        Map<String, Object> parameter = getParameter();

        try {
            boolean result = notifyWork(url, parameter);
            if (result) {
                LOGGER.info("AbstractWebServerWarmUpChannelHandler notice, handlerName: {}, doHandle notice, " +
                        "notifyWork success, url: {}, parameter: {}", handlerName, url, parameter);
                return true;
            } else {
                LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, doHandle failed, " +
                        "notifyWork failed, url: {}, parameter: {}", handlerName, url, parameter);
                return false;
            }
        } catch (Throwable ex) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, doHandle failed, " +
                    "url: {}, parameter: {}, throwable: ", handlerName, url, parameter, ex);
            return false;
        }
    }

    @Override
    public void doNextHandle() {
        String handlerName = getHandlerName();

        WebServerChannelHandler nextHandler = getNextHandler();
        if (nextHandler == null) {
            LOGGER.info("AbstractWebServerWarmUpChannelHandler notice, handlerName: {}, doNextHandle ignored, " +
                    "nextHandler is null", handlerName);
            return;
        }

        int timeout = getTimeout();
        if (timeout <= 0) {
            LOGGER.warn("AbstractWebServerWarmUpChannelHandler warning, handlerName: {}, doNextHandle warning, " +
                    "sleep abort, timeout: {}", handlerName, timeout);
            nextHandler.handle();
            return;
        }

        boolean blocking = getBlocking();
        if (blocking) {
            waitWork(timeout);
            nextHandler.handle();
            return;
        }

        ThreadPool.execute(() -> {
            waitWork(timeout);
            nextHandler.handle();
        });
    }

    /**
     * 通知预热服务器
     *
     * @param url       预热服务器地址
     * @param parameter 预热配置
     * @return 通知成功？
     */
    public boolean notifyWork(String url, Map<String, Object> parameter) {
        String handlerName = getHandlerName();

        if (url == null) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, notifyWork failed, " +
                    "url must not be null", handlerName);
            return false;
        }

        if (url.isEmpty()) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, notifyWork failed, " +
                    "url must not be empty", handlerName);
            return false;
        }

        if (parameter == null) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, notifyWork failed, " +
                    "parameter must not be null, url: {}", handlerName, url);
            return false;
        }

        if (parameter.isEmpty()) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, notifyWork failed, " +
                    "parameter must not be empty, url: {}", handlerName, url);
            return false;
        }

        boolean result = httpRequest(url, parameter);
        if (result) {
            LOGGER.info("AbstractWebServerWarmUpChannelHandler notice, handlerName: {}, notifyWork notice, " +
                    "httpRequest success, url: {}, parameter: {}", handlerName, url, parameter);
            return true;
        } else {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, notifyWork failed, " +
                    "httpRequest failed, url: {}, parameter: {}", handlerName, url, parameter);
            return false;
        }
    }

    /**
     * 等待预热完成
     *
     * @param seconds 等待时长
     */
    public void waitWork(int seconds) {
        String handlerName = getHandlerName();

        if (seconds <= 0) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, waitWork failed, " +
                    "seconds must be greater than 0, seconds: {}", handlerName, seconds);
            return;
        }

        try {
            LOGGER.info("AbstractWebServerWarmUpChannelHandler notice, handlerName: {}, waitWork notice, " +
                    "sleep start, seconds: {}", handlerName, seconds);
            Thread.sleep(seconds * 1000L);
            LOGGER.info("AbstractWebServerWarmUpChannelHandler notice, handlerName: {}, waitWork notice, " +
                    "sleep stop, seconds: {}", handlerName, seconds);
        } catch (InterruptedException ex) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, waitWork failed, " +
                    "seconds: {}, throwable: ", handlerName, seconds, ex);
        }
    }

    /**
     * 发送Http请求
     *
     * @param url       请求地址
     * @param parameter 请求参数
     * @return 请求成功？
     */
    public abstract boolean httpRequest(String url, Map<String, Object> parameter);

    /**
     * Http请求参数
     *
     * @return 请求参数
     */
    public abstract Map<String, Object> getParameter();

    /**
     * 预热服务器地址
     *
     * @return 请求地址
     */
    public String getUrl() {
        String handlerName = getHandlerName();

        WarmUpProperties properties = getProperties();
        if (properties != null) {
            return properties.getUrl();
        } else {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, getUrl failed, " +
                    "properties must not be null", handlerName);
            return null;
        }
    }

    /**
     * 预热时长
     *
     * @return 超时，单位：秒
     */
    public int getTimeout() {
        String handlerName = getHandlerName();

        WarmUpProperties properties = getProperties();
        if (properties == null) {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, getTimeout failed, " +
                    "properties must not be null", handlerName);
            return 0;
        }

        Integer timeout = properties.getTimeout();
        if (timeout != null) {
            return timeout;
        } else {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, getTimeout failed, " +
                    "timeout must not be null, properties: {}", handlerName, properties);
            return 0;
        }
    }

    /**
     * 阻塞？
     *
     * @return True-阻塞、其他-非阻塞
     */
    public boolean getBlocking() {
        String handlerName = getHandlerName();

        WarmUpProperties properties = getProperties();
        if (properties != null) {
            Boolean blocking = properties.getBlocking();
            return Boolean.TRUE.equals(blocking);
        } else {
            LOGGER.error("AbstractWebServerWarmUpChannelHandler failed, handlerName: {}, getBlocking failed, " +
                    "properties must not be null", handlerName);
            return false;
        }
    }

    public WarmUpProperties getProperties() {
        return properties;
    }

}
