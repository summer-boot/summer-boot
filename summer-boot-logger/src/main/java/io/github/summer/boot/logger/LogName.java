package io.github.summer.boot.logger;

import org.slf4j.MDC;

/**
 * 日志频道
 *
 * @author changebooks@qq.com
 */
public final class LogName {
    /**
     * 键名
     */
    public static final String KEY_NAME = "name";

    private LogName() {
    }

    /**
     * 获取日志频道
     *
     * @return 日志频道
     */
    public static String get() {
        return MDC.get(KEY_NAME);
    }

    /**
     * 设置日志频道
     *
     * @param name 新的日志频道
     */
    public static void set(String name) {
        MDC.put(KEY_NAME, name);
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
