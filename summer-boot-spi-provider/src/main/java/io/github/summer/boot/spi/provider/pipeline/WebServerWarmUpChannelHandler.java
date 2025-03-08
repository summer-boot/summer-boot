package io.github.summer.boot.spi.provider.pipeline;

import io.github.summer.boot.autoconfigure.WarmUpProperties;
import io.github.summer.boot.http.HttpResult;
import io.github.summer.boot.http.Result;
import io.github.summer.boot.spi.provider.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 预热
 *
 * @author changebooks@qq.com
 */
public class WebServerWarmUpChannelHandler extends AbstractWebServerWarmUpChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerWarmUpChannelHandler.class);

    /**
     * 服务管理
     */
    private final WebServerService service;

    public WebServerWarmUpChannelHandler(WarmUpProperties properties, WebServerService service,
                                         WebServerChannelHandler nextHandler) {
        super(properties, nextHandler);
        this.service = service;
    }

    public WebServerWarmUpChannelHandler(WarmUpProperties properties, WebServerService service) {
        super(properties, null);
        this.service = service;
    }

    @Override
    public boolean httpRequest(String url, Map<String, Object> parameter) {
        Result result = doHttpRequest(url, parameter);
        if (result == null) {
            LOGGER.error("WebServerWarmUpChannelHandler failed, httpRequest failed, " +
                    "result must not be null, url: {}, parameter: {}", url, parameter);
            return false;
        }

        HttpResult response = result.getResponse();
        if (response == null) {
            LOGGER.error("WebServerWarmUpChannelHandler failed, httpRequest failed, " +
                    "response must not be null, url: {}, parameter: {}, result: {}", url, parameter, result);
            return false;
        }

        String data = response.getData();

        if (response.isOk()) {
            LOGGER.info("WebServerWarmUpChannelHandler notice, httpRequest success, " +
                    "url: {}, parameter: {}, data: {}", url, parameter, data);
            return true;
        } else {
            int statusCode = response.getStatusCode();
            LOGGER.error("WebServerWarmUpChannelHandler failed, httpRequest failed, " +
                    "url: {}, parameter: {}, statusCode: {}, data: {}", url, parameter, statusCode, data);
            return false;
        }
    }

    /**
     * 发送Http请求
     *
     * @param url       请求地址
     * @param parameter 请求参数
     * @return 请求结果
     */
    public Result doHttpRequest(String url, Map<String, Object> parameter) {
        try {
            return WebServerUtils.doPost(url, parameter);
        } catch (Throwable ex) {
            LOGGER.error("WebServerWarmUpChannelHandler failed, doHttpRequest failed, url: {}, parameter: {}, throwable:",
                    url, parameter, ex);
            return null;
        }
    }

    @Override
    public Map<String, Object> getParameter() {
        WebServerSchema schema = service.newWebSchema();

        if (schema == null) {
            LOGGER.error("WebServerWarmUpChannelHandler failed, getParameter failed, schema must not be null");
            return null;
        }

        Map<String, Object> parameter = new HashMap<>(8);

        String server = schema.getServer();
        parameter.put("server", server);

        String host = schema.getHost();
        parameter.put("host", host);

        int port = schema.getPort();
        parameter.put("port", port);

        String group = schema.getGroup();
        parameter.put("group", group);

        String cluster = schema.getCluster();
        parameter.put("cluster", cluster);

        return parameter;
    }

}
