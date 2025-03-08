package io.github.summer.boot.spi.provider;

/**
 * 服务管理
 *
 * @author changebooks@qq.com
 */
public interface WebServerService {
    /**
     * 初始化服务
     *
     * @param webPort 服务端口
     * @return 初始化成功？
     */
    boolean onWebInitialized(int webPort);

    /**
     * 注册
     *
     * @return 注册成功？
     */
    boolean register();

    /**
     * 注销
     *
     * @return 注销成功？
     */
    boolean deregister();

    /**
     * 已注册？
     *
     * @return True-已注册、False-其他
     */
    boolean isRegister();

    /**
     * 已注销？
     *
     * @return True-已注销、False-其他
     */
    boolean isDeregister();

    /**
     * 上线
     *
     * @return 上线成功？
     */
    boolean online();

    /**
     * 下线
     *
     * @return 下线成功？
     */
    boolean offline();

    /**
     * 已上线？
     *
     * @return True-已上线、False-其他
     */
    boolean isOnline();

    /**
     * 已下线？
     *
     * @return True-已下线、False-其他
     */
    boolean isOffline();

    /**
     * 服务概要
     *
     * @return the {@link WebServerSchema} instance
     */
    WebServerSchema newWebSchema();

}
