package io.github.summer.boot.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求日志
 *
 * @author changebooks@qq.com
 */
public class HttpServletLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServletLog.class);

    /**
     * 写日志
     *
     * @param request  the {@link HttpServletRequest} instance
     * @param response the {@link HttpServletResponse} instance
     */
    public void writeLog(HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String requestContentType = request.getContentType();
        String requestHeader = getRequestHeader(request);
        String requestParameter = getRequestParameter(request);
        String requestBody = getRequestBody(request);
        String responseBody = getResponseBody(response);

        LOGGER.info("HttpServlet notice, " +
                        "uri: {}, method: {}, contentType: {}, header: {}, parameter: {}, request: {}, response: {}",
                requestUri, requestMethod, requestContentType, requestHeader, requestParameter, requestBody, responseBody);
    }

    /**
     * 请求标头
     *
     * @param request the {@link HttpServletRequest} instance
     * @return the String
     */
    public String getRequestHeader(HttpServletRequest request) {
        return null;
    }

    /**
     * 请求参数
     *
     * @param request the {@link HttpServletRequest} instance
     * @return the String
     */
    public String getRequestParameter(HttpServletRequest request) {
        return null;
    }

    /**
     * 请求内容
     *
     * @param request the {@link HttpServletRequest} instance
     * @return the String
     */
    public String getRequestBody(HttpServletRequest request) {
        return null;
    }

    /**
     * 响应内容
     *
     * @param response the {@link HttpServletResponse} instance
     * @return the String
     */
    public String getResponseBody(HttpServletResponse response) {
        return null;
    }

}
