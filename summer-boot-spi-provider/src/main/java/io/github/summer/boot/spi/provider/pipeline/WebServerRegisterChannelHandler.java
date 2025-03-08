package io.github.summer.boot.spi.provider.pipeline;

import io.github.summer.boot.spi.provider.AbstractWebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注册
 *
 * @author changebooks@qq.com
 */
public class WebServerRegisterChannelHandler extends AbstractWebServerChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerRegisterChannelHandler.class);

    /**
     * 延迟等待时长，单位：毫秒
     */
    private static final long SLEEP_TIME = 500;

    /**
     * 服务管理
     */
    private final WebServerService service;

    public WebServerRegisterChannelHandler(WebServerService service, WebServerChannelHandler nextHandler) {
        super(nextHandler);
        this.service = service;
    }

    public WebServerRegisterChannelHandler(WebServerService service) {
        super(null);
        this.service = service;
    }

    @Override
    public boolean doHandle() {
        return service.register();
    }

    @Override
    public void doNextHandle() {
        WebServerChannelHandler nextHandler = getNextHandler();
        if (nextHandler == null) {
            LOGGER.info("WebServerRegisterChannelHandler notice, doNextHandle ignored, nextHandler is null");
            return;
        }

        while (true) {
            boolean register = service.isRegister();
            if (register) {
                LOGGER.info("WebServerRegisterChannelHandler notice, doNextHandle continue, service is registered");
                break;
            } else {
                LOGGER.info("WebServerRegisterChannelHandler notice, doNextHandle wait, service is registering");
                sleep();
            }
        }

        nextHandler.handle();
    }

    /**
     * 延迟等待
     */
    public void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException ex) {
            LOGGER.error("WebServerRegisterChannelHandler failed, sleep failed, sleep time: {}, throwable: ", SLEEP_TIME, ex);
        }
    }

}
