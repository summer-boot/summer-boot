package io.github.summer.boot.spi.provider;

/**
 * 管理服务生命周期
 *
 * @author changebooks@qq.com
 */
public interface WebServerChannelHandler {
    /**
     * 处理流程
     */
    void handle();

}
