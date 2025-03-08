package io.github.summer.boot.web;

import io.github.summer.boot.logger.LogTraceId;
import io.github.summer.boot.util.AssertUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 追溯id
 *
 * @author changebooks@qq.com
 */
@WebFilter(filterName = "traceIdFilter", urlPatterns = "/**")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends HttpFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceIdFilter.class);

    /**
     * 键名
     */
    public static final String KEY_NAME = "log_tid";

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        try {
            LOGGER.debug("TraceIdFilter notice, doFilter notice, processLog start");
            processLog(request);
            LOGGER.debug("TraceIdFilter notice, doFilter notice, processLog stop");
        } catch (Throwable ex) {
            LOGGER.error("TraceIdFilter failed, doFilter failed, throwable: ", ex);
        }

        super.doFilter(request, response, chain);
    }

    /**
     * 从请求标头获取追溯id，设置日志上下文
     *
     * @param request the {@link HttpServletRequest} instance
     */
    public void processLog(HttpServletRequest request) {
        AssertUtils.nonNull(request, "request");

        String traceId = request.getHeader(KEY_NAME);
        if (StringUtils.hasText(traceId)) {
            LogTraceId.set(traceId);
        } else {
            LogTraceId.init();
        }
    }

    @Override
    public void destroy() {
        LogTraceId.remove();
    }

}
