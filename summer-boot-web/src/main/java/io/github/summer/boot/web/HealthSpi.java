package io.github.summer.boot.web;

import io.github.summer.boot.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 健康检查
 *
 * @author changebooks@qq.com
 */
public interface HealthSpi {
    /**
     * 提供服务？
     *
     * @return True-提供服务、False-拒绝服务
     */
    @GetMapping(value = "/check")
    Result<Boolean> check();

    /**
     * 上线
     *
     * @return 上线成功？
     */
    @PutMapping(value = "/online")
    Result<Boolean> online();

    /**
     * 下线
     *
     * @return 下线成功？
     */
    @PutMapping(value = "/offline")
    Result<Boolean> offline();

    /**
     * 已上线？
     *
     * @return True-已上线、False-其他
     */
    @GetMapping(value = "/online")
    Result<Boolean> isOnline();

    /**
     * 已下线？
     *
     * @return True-已下线、False-其他
     */
    @GetMapping(value = "/offline")
    Result<Boolean> isOffline();

    /**
     * 注册
     *
     * @return 注册成功？
     */
    @PutMapping(value = "/register")
    Result<Boolean> register();

    /**
     * 注销
     *
     * @return 注销成功？
     */
    @PutMapping(value = "/deregister")
    Result<Boolean> deregister();

    /**
     * 已注册？
     *
     * @return True-已注册、False-其他
     */
    @GetMapping(value = "/register")
    Result<Boolean> isRegister();

    /**
     * 已注销？
     *
     * @return True-已注销、False-其他
     */
    @GetMapping(value = "/deregister")
    Result<Boolean> isDeregister();

}
