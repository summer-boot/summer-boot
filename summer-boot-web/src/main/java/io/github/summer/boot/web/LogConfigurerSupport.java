package io.github.summer.boot.web;

/**
 * 日志配置
 *
 * @author changebooks@qq.com
 */
public class LogConfigurerSupport {
    /**
     * 追溯id
     *
     * @return the {@link TraceIdFilter} instance
     */
    public TraceIdFilter traceIdFilter() {
        return null;
    }

    /**
     * 日志id
     *
     * @return the {@link LogIdFilter} instance
     */
    public LogIdFilter logIdFilter() {
        return null;
    }

    /**
     * 请求日志
     *
     * @return the {@link HttpServletLog} instance
     */
    public HttpServletLog httpServletLog() {
        return null;
    }

}
