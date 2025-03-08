package io.github.summer.boot.spi.provider.pipeline;

import io.github.summer.boot.spi.provider.AbstractWebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerChannelHandler;
import io.github.summer.boot.spi.provider.WebServerService;

/**
 * 下线
 *
 * @author changebooks@qq.com
 */
public class WebServerOfflineChannelHandler extends AbstractWebServerChannelHandler {
    /**
     * 服务管理
     */
    private final WebServerService service;

    public WebServerOfflineChannelHandler(WebServerService service, WebServerChannelHandler nextHandler) {
        super(nextHandler);
        this.service = service;
    }

    public WebServerOfflineChannelHandler(WebServerService service) {
        super(null);
        this.service = service;
    }

    @Override
    public boolean doHandle() {
        return service.offline();
    }

}
