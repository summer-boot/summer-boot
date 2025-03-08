package io.github.summer.boot.spi.subscriber;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.summer.boot.logger.LogId;
import org.springframework.util.StringUtils;

/**
 * 从日志上下文获取日志id，设置请求标头
 *
 * @author changebooks@qq.com
 */
public class SpiLogIdInterceptor implements RequestInterceptor {
    /**
     * 键名
     */
    public static final String KEY_NAME = "log_id";

    @Override
    public void apply(RequestTemplate template) {
        if (LogId.isEmpty()) {
            LogId.init();
        }

        String logId = LogId.get();
        if (StringUtils.hasText(logId)) {
            template.header(KEY_NAME, logId);
        }
    }

}
