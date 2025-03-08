package io.github.summer.boot.spi.provider;

/**
 * 配置服务管理
 * <pre>
 * {@link WebServerService}
 * {@link WebServerManager}
 * </pre>
 *
 * @author changebooks@qq.com
 */
public class WebServerServiceConfigurerSupport {
    /**
     * 服务管理
     *
     * @param webServerManager the {@link WebServerManager} instance
     * @return the {@link WebServerService} instance
     */
    public WebServerService webServerService(WebServerManager webServerManager) {
        return new WebServerServiceImpl(webServerManager);
    }

    /**
     * 注册注销服务
     *
     * @return the {@link WebServerManager} instance
     */
    public WebServerManager webServerManager() {
        return null;
    }

}
