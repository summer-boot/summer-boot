package io.github.summer.boot.logger;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * 日志id
 *
 * @author changebooks@qq.com
 */
public final class LogId {
    /**
     * 键名
     */
    public static final String KEY_NAME = "id";

    /**
     * 生成新id的函数接口
     */
    private static Gen gen = new Gen() {
    };

    private LogId() {
    }

    /**
     * 获取日志id
     *
     * @return 日志id
     */
    public static String get() {
        return MDC.get(KEY_NAME);
    }

    /**
     * 设置日志id
     *
     * @param id 新的日志id
     */
    public static void set(String id) {
        MDC.put(KEY_NAME, id);
    }

    /**
     * 删除日志id
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

    /**
     * 初始化日志id
     */
    public static void init() {
        if (isEmpty()) {
            set(gen.nextId());
        }
    }

    /**
     * 设置函数接口
     *
     * @param gen 函数接口
     */
    public static void setGen(Gen gen) {
        if (gen != null) {
            LogId.gen = gen;
        }
    }

    /**
     * 函数接口
     */
    public interface Gen {
        /**
         * 生成新id
         *
         * @return id
         */
        default String nextId() {
            return UUID.randomUUID().toString();
        }

    }

}
