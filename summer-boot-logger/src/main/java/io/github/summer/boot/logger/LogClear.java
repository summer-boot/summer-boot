package io.github.summer.boot.logger;

import org.slf4j.MDC;

/**
 * 日志清理
 *
 * @author changebooks@qq.com
 */
public final class LogClear {

    private LogClear() {
    }

    /**
     * 清空上下文
     */
    public static void clear() {
        MDC.clear();
    }

}
