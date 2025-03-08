package io.github.summer.boot.elastic;

import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截ES响应，打日志
 *
 * @author changebooks@qq.com
 */
public class ElasticLogRespInterceptor implements HttpResponseInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticLogRespInterceptor.class);

    @Override
    public void process(HttpResponse response, HttpContext context) {
        if (disabled()) {
            return;
        }

        if (response == null) {
            LOGGER.error("response must not be null");
            return;
        }

        StatusLine statusLine = response.getStatusLine();
        if (statusLine == null) {
            LOGGER.error("statusLine must not be null");
            return;
        }

        int statusCode = statusLine.getStatusCode();
        log(statusCode, context);
    }

    /**
     * 打日志
     *
     * @param statusCode 状态码
     * @param context    http上下文
     */
    public void log(int statusCode, HttpContext context) {
        if (statusCode == HttpStatus.SC_OK) {
            LOGGER.info("response, statusCode: {}", statusCode);
        } else {
            LOGGER.error("response, statusCode: {}", statusCode);
        }
    }

    /**
     * 关闭日志？
     *
     * @return 默认，开启日志
     */
    public boolean disabled() {
        return false;
    }

}
