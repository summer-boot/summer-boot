package io.github.summer.boot.logger;

import org.slf4j.MDC;

/**
 * 日志索引
 *
 * @author changebooks@qq.com
 */
public final class LogIndex {
    /**
     * 键名
     */
    public static final String KEY_NAME = "index";

    /**
     * 拼接索引的函数接口
     */
    private static Joiner joiner = new Joiner() {
    };

    private LogIndex() {
    }

    /**
     * 获取索引
     *
     * @return 索引
     */
    public static String get() {
        return MDC.get(KEY_NAME);
    }

    /**
     * 新增索引
     *
     * @param index 待新增的索引
     */
    public static void put(String index) {
        String value = joiner.join(get(), index);
        MDC.put(KEY_NAME, value);
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
     * 设置函数接口
     *
     * @param joiner 函数接口
     */
    public static void setJoiner(Joiner joiner) {
        if (joiner != null) {
            LogIndex.joiner = joiner;
        }
    }

    /**
     * 函数接口
     */
    public interface Joiner {
        /**
         * 分隔符
         */
        String DELIMITER = " ";

        /**
         * 拼接索引
         *
         * @param index 当前索引
         * @param other 待拼接的索引
         * @return 拼接后的索引
         */
        default String join(String index, String other) {
            if (index == null || index.isEmpty()) {
                return other;
            }

            if (other == null || other.isEmpty()) {
                return index;
            }

            return index + DELIMITER + other;
        }

    }

}
