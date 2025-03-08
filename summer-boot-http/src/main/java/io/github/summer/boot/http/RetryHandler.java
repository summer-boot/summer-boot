package io.github.summer.boot.http;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * 重试
 *
 * @author changebooks@qq.com
 */
public class RetryHandler implements HttpRequestRetryHandler {
    /**
     * 最大重试次数
     */
    private final int maxRetries;

    public RetryHandler(int maxRetries) {
        if (maxRetries <= 0) {
            throw new IllegalArgumentException("maxRetries must not be less than 0");
        }

        this.maxRetries = maxRetries;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        // 超过最大重试次数，不重试
        if (executionCount >= maxRetries) {
            return false;
        }

        // 丢失连接，重试
        if (exception instanceof NoHttpResponseException) {
            return true;
        }

        // SSL握手异常，不重试
        if (exception instanceof SSLHandshakeException) {
            return false;
        }

        // SSL异常，不重试
        if (exception instanceof SSLException) {
            return false;
        }

        // 超时，不重试
        if (exception instanceof InterruptedIOException) {
            return false;
        }

        // 目标服务器不可达，不重试
        if (exception instanceof UnknownHostException) {
            return false;
        }

        // 支持幂等，重试
        return supportIdempotent(context);
    }

    /**
     * 支持幂等？PUT, GET, HEAD
     *
     * @param httpContext the {@link HttpContext} instance
     * @return PUT, GET, HEAD, ... ? true : false
     */
    public boolean supportIdempotent(HttpContext httpContext) {
        return RequestUtils.supportIdempotent(httpContext);
    }

}
