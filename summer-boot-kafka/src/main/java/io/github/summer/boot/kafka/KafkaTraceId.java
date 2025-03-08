package io.github.summer.boot.kafka;

import io.github.summer.boot.logger.LogTraceId;
import org.apache.kafka.common.header.Headers;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 追溯id
 *
 * @author changebooks@qq.com
 */
public final class KafkaTraceId {
    /**
     * 键名
     */
    public static final String KEY_NAME = "log_tid";

    private KafkaTraceId() {
    }

    /**
     * 消费之前
     * 从已接收消息获取追溯id，设置日志上下文
     *
     * @param headers 已接收消息的标头
     */
    public static void onConsume(Headers headers) {
        Objects.requireNonNull(headers, "headers must not be null");

        String traceId = KafkaHeaders.get(headers, KEY_NAME);
        if (StringUtils.hasText(traceId)) {
            LogTraceId.set(traceId);
        } else {
            LogTraceId.init();
        }
    }

    /**
     * 发送之前
     * 从日志上下文获取追溯id，设置待发送消息
     *
     * @param headers 待发送的消息的标头
     */
    public static void onSend(Headers headers) {
        Objects.requireNonNull(headers, "headers must not be null");

        String traceId = LogTraceId.get();
        if (StringUtils.hasText(traceId)) {
            KafkaHeaders.set(headers, KEY_NAME, traceId);
            return;
        }

        traceId = KafkaHeaders.get(headers, KEY_NAME);
        if (StringUtils.hasText(traceId)) {
            return;
        }

        LogTraceId.init();
        traceId = LogTraceId.get();
        KafkaHeaders.set(headers, KEY_NAME, traceId);
    }

}
