package io.github.summer.boot.http;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.protocol.HttpContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

/**
 * 请求常用工具
 *
 * @author changebooks@qq.com
 */
public final class RequestUtils {

    private RequestUtils() {
    }

    /**
     * 拼接请求参数，Get Path
     *
     * @param uri    the uri string
     * @param params the map
     * @return uri?params
     * @throws URISyntaxException if the given string violates RFC&nbsp;2396, as augmented by the above deviations
     */
    public static String joinParams(String uri, Map<String, Object> params) throws URISyntaxException {
        if (uri == null || uri.isEmpty()) {
            throw new IllegalArgumentException("uri must not be empty");
        }

        if (params == null) {
            return uri;
        } else {
            return newUri(uri, params).toString();
        }
    }

    /**
     * Build URI
     *
     * @param uri    the uri string
     * @param params the map
     * @return a {@link URI} instance
     * @throws URISyntaxException if the given string violates RFC&nbsp;2396, as augmented by the above deviations
     */
    public static URI newUri(String uri, Map<String, Object> params) throws URISyntaxException {
        if (uri == null || uri.isEmpty()) {
            throw new IllegalArgumentException("uri must not be empty");
        }

        URIBuilder uriBuilder = new URIBuilder(uri);

        if (params == null) {
            return uriBuilder.build();
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry != null) {
                String key = entry.getKey();
                Object obj = entry.getValue();
                String value = (obj == null) ? "" : obj.toString();
                uriBuilder.setParameter(key, value);
            }
        }

        return uriBuilder.build();
    }

    /**
     * Add Headers to HttpRequest
     *
     * @param httpRequest the {@link HttpRequestBase} instance
     * @param headers     the map
     */
    public static void addHeaders(HttpRequestBase httpRequest, Map<String, Object> headers) {
        Objects.requireNonNull(httpRequest, "httpRequest must not be null");

        if (headers == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            if (entry != null) {
                String key = entry.getKey();
                Object obj = entry.getValue();
                String value = (obj == null) ? "" : obj.toString();
                httpRequest.addHeader(key, value);
            }
        }
    }

    /**
     * 支持幂等？PUT, GET, HEAD
     *
     * @param httpContext the {@link HttpContext} instance
     * @return PUT, GET, HEAD, ... ? true : false
     */
    public static boolean supportIdempotent(HttpContext httpContext) {
        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        HttpRequest httpRequest = clientContext.getRequest();
        return !(httpRequest instanceof HttpEntityEnclosingRequest);
    }

}
