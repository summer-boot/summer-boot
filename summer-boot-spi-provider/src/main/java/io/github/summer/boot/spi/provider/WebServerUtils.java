package io.github.summer.boot.spi.provider;

import io.github.summer.boot.http.EntityUtils;
import io.github.summer.boot.http.HttpRequest;
import io.github.summer.boot.http.RequestUtils;
import io.github.summer.boot.http.Result;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 服务工具集
 *
 * @author changebooks@qq.com
 */
public final class WebServerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServerUtils.class);

    private WebServerUtils() {
    }

    /**
     * 服务端口
     *
     * @param webServer Web服务
     * @return &ge; 0
     */
    public static int getWebPort(WebServer webServer) {
        if (webServer == null) {
            LOGGER.error("WebServerUtils failed, getWebPort failed, webServer must not be null");
            return 0;
        }

        int port = webServer.getPort();
        if (port > 0) {
            LOGGER.debug("WebServerUtils notice, getWebPort success, port: {}", port);
            return port;
        } else {
            LOGGER.error("WebServerUtils failed, getWebPort failed, port must be greater than 0, port: {}", port);
            return 0;
        }
    }

    /**
     * 发送Get请求
     *
     * @param url       请求地址
     * @param parameter 请求参数
     * @return 请求结果
     * @throws URISyntaxException 解析URI错误
     * @throws IOException        发送请求失败
     */
    public static Result doGet(String url, Map<String, Object> parameter) throws URISyntaxException, IOException {
        URI uri = RequestUtils.newUri(url, parameter);
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            return HttpRequest.doGet(httpClient, httpGet);
        }
    }

    /**
     * 发送Post请求
     *
     * @param url       请求地址
     * @param parameter 请求参数
     * @return 请求结果
     * @throws IOException 发送请求失败
     */
    public static Result doPost(String url, Map<String, Object> parameter) throws IOException {
        HttpEntity httpEntity = EntityUtils.toForm(parameter);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            return HttpRequest.doPost(httpClient, httpPost);
        }
    }

}
