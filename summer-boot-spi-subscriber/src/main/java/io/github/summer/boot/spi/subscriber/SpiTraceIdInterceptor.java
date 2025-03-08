package io.github.summer.boot.spi.subscriber;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.summer.boot.logger.LogTraceId;
import org.springframework.util.StringUtils;

/**
 * 从日志上下文获取追溯id，设置请求标头
 *
 * @author changebooks@qq.com
 */
public class SpiTraceIdInterceptor implements RequestInterceptor {
    /**
     * 键名
     */
    public static final String KEY_NAME = "log_tid";

    @Override
    public void apply(RequestTemplate template) {
        if (LogTraceId.isEmpty()) {
            LogTraceId.init();
        }

        String traceId = LogTraceId.get();
        if (StringUtils.hasText(traceId)) {
            template.header(KEY_NAME, traceId);
        }
    }

}
