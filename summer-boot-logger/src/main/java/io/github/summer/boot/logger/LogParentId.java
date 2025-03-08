package io.github.summer.boot.logger;

import org.slf4j.MDC;

/**
 * 父日志id
 *
 * @author changebooks@qq.com
 */
public final class LogParentId {
    /**
     * 键名
     */
    public static final String KEY_NAME = "pid";

    private LogParentId() {
    }

    /**
     * 获取父日志id
     *
     * @return 父日志id
     */
    public static String get() {
        return MDC.get(KEY_NAME);
    }

    /**
     * 设置父日志id
     *
     * @param id 新的父日志id
     */
    public static void set(String id) {
        MDC.put(KEY_NAME, id);
    }

    /**
     * 删除父日志id
     */
    public static void remove() {
        MDC.remove(KEY_NAME);
    }

    /**
     * 未设置过？
     *
     * @return empty ? true : false
     */
    public static boolean isEmpty() {
        String id = get();
        return id == null || id.isEmpty();
    }

}
