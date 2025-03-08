package io.github.summer.boot.spi.provider;

import io.github.summer.boot.spi.provider.pipeline.*;

/**
 * 配置服务生命周期
 * <pre>
 * {@link WebServerInitializeChannelHandler}
 * {@link WebServerWarmUpChannelHandler}
 * {@link WebServerRegisterChannelHandler}
 * {@link WebServerDeregisterChannelHandler}
 * {@link WebServerOnlineChannelHandler}
 * {@link WebServerOfflineChannelHandler}
 * </pre>
 *
 * @author changebooks@qq.com
 */
public class WebServerPipelineConfigurerSupport {
    /**
     * 初始化Web服务
     *
     * @return the {@link WebServerInitializeChannelHandler} instance
     */
    public WebServerInitializeChannelHandler webServerChannelInitializer() {
        return null;
    }

    /**
     * 预热
     *
     * @return the {@link WebServerWarmUpChannelHandler} instance
     */
    public WebServerWarmUpChannelHandler webServerWarmUpChannelHandler() {
        return null;
    }

    /**
     * 注册
     *
     * @return the {@link WebServerRegisterChannelHandler} instance
     */
    public WebServerRegisterChannelHandler webServerRegisterChannelHandler() {
        return null;
    }

    /**
     * 注销
     *
     * @return the {@link WebServerDeregisterChannelHandler} instance
     */
    public WebServerDeregisterChannelHandler webServerDeregisterChannelHandler() {
        return null;
    }

    /**
     * 上线
     *
     * @return the {@link WebServerOnlineChannelHandler} instance
     */
    public WebServerOnlineChannelHandler webServerOnlineChannelHandler() {
        return null;
    }

    /**
     * 下线
     *
     * @return the {@link WebServerOfflineChannelHandler} instance
     */
    public WebServerOfflineChannelHandler webServerOfflineChannelHandler() {
        return null;
    }

}
