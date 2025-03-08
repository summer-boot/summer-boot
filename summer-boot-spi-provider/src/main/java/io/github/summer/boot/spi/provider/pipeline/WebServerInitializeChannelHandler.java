package io.github.summer.boot.spi.provider.pipeline;

import io.github.summer.boot.spi.provider.AbstractWebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerService;
import io.github.summer.boot.spi.provider.WebServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;

/**
 * 初始化Web服务
 *
 * @author changebooks@qq.com
 */
public class WebServerInitializeChannelHandler extends AbstractWebServerChannelHandler implements ApplicationListener<WebServerInitializedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerInitializeChannelHandler.class);

    /**
     * 服务管理
     */
    private final WebServerService service;

    /**
     * 服务端口
     */
    private int webPort;

    public WebServerInitializeChannelHandler(WebServerService service, WebServerChannelHandler nextHandler) {
        super(nextHandler);
        this.service = service;
    }

    public WebServerInitializeChannelHandler(WebServerService service) {
        super(null);
        this.service = service;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webInitializedEvent) {
        LOGGER.info("WebServerInitializeChannelHandler notice, onApplicationEvent start");

        LOGGER.info("WebServerInitializeChannelHandler notice, onApplicationEvent notice, initializeWebPort start");
        initializeWebPort(webInitializedEvent);
        LOGGER.info("WebServerInitializeChannelHandler notice, onApplicationEvent notice, initializeWebPort stop, webPort: {}", webPort);

        handle();

        LOGGER.info("WebServerInitializeChannelHandler notice, onApplicationEvent stop");
    }

    /**
     * 初始化服务端口
     *
     * @param webInitializedEvent 初始化Web服务的事件
     */
    public void initializeWebPort(WebServerInitializedEvent webInitializedEvent) {
        if (webInitializedEvent != null) {
            WebServer webServer = webInitializedEvent.getWebServer();
            int webPort = WebServerUtils.getWebPort(webServer);
            if (webPort > 0) {
                LOGGER.info("WebServerInitializeChannelHandler notice, initializeWebPort success, webPort: {}", webPort);
                this.webPort = webPort;
            } else {
                LOGGER.error("WebServerInitializeChannelHandler failed, initializeWebPort failed, webPort must be greater than 0, webPort: {}", webPort);
            }
        } else {
            LOGGER.error("WebServerInitializeChannelHandler failed, initializeWebPort failed, webInitializedEvent must not be null");
        }
    }

    @Override
    public boolean doHandle() {
        return service.onWebInitialized(webPort);
    }

}
