package io.github.summer.boot.spi.provider.pipeline;

import io.github.summer.boot.spi.provider.AbstractWebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerService;

/**
 * 注销
 *
 * @author changebooks@qq.com
 */
public class WebServerDeregisterChannelHandler extends AbstractWebServerChannelHandler {
    /**
     * 服务管理
     */
    private final WebServerService service;

    public WebServerDeregisterChannelHandler(WebServerService service, WebServerChannelHandler nextHandler) {
        super(nextHandler);
        this.service = service;
    }

    public WebServerDeregisterChannelHandler(WebServerService service) {
        super(null);
        this.service = service;
    }

    @Override
    public boolean doHandle() {
        return service.deregister();
    }

}
