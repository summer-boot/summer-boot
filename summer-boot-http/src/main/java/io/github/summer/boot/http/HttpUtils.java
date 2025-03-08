package io.github.summer.boot.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Http请求
 *
 * <pre>
 * CloseableHttpClient httpClient = HttpClientBuilder.create().build();
 * HttpGet httpGet = new HttpGet("http://***.com");
 *
 * HttpResult result = Http.execute(httpClient, httpGet);
 * System.out.println(result);
 * </pre>
 *
 * @author changebooks@qq.com
 */
public final class HttpUtils {
    /**
     * 默认的字符编码
     */
    private static final Charset DEFAULT_CHARSET = Consts.UTF_8;

    private HttpUtils() {
    }

    /**
     * Send Http Request
     *
     * @param httpClient  HttpClientBuilder.create().build();
     * @param httpRequest HttpGet, HttpPost, HttpPut, HttpDelete
     * @return HttpResult
     * @throws IOException if an error occurs reading the input stream
     */
    public static HttpResult execute(final CloseableHttpClient httpClient, HttpRequestBase httpRequest) throws IOException {
        return execute(httpClient, httpRequest, DEFAULT_CHARSET);
    }

    /**
     * Send Http Request
     *
     * @param httpClient  HttpClientBuilder.create().build();
     * @param httpRequest HttpGet, HttpPost, HttpPut, HttpDelete
     * @param charset     Charset，Default: {@link #DEFAULT_CHARSET}
     * @return HttpResult
     * @throws IOException if an error occurs reading the input stream
     */
    public static HttpResult execute(final CloseableHttpClient httpClient, HttpRequestBase httpRequest, final Charset charset) throws IOException {
        Objects.requireNonNull(httpClient, "httpClient must not be null");
        Objects.requireNonNull(httpRequest, "httpRequest must not be null");

        CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);

        try (httpResponse) {
            Objects.requireNonNull(httpResponse, "httpResponse must not be null");
            return readResponse(httpResponse, charset);
        }
    }

    /**
     * Read Response
     *
     * @param httpResponse CloseableHttpClient.execute(HttpRequestBase)
     * @param charset      Charset，Default: {@link #DEFAULT_CHARSET}
     * @return HttpResult
     * @throws IOException if an error occurs reading the input stream
     */
    public static HttpResult readResponse(final CloseableHttpResponse httpResponse, final Charset charset) throws IOException {
        Objects.requireNonNull(httpResponse, "httpResponse must not be null");

        StatusLine statusLine = httpResponse.getStatusLine();
        Objects.requireNonNull(statusLine, "statusLine must not be null");

        HttpResult result = new HttpResult();
        result.setStatusCode(statusLine.getStatusCode());
        result.setHeaders(httpResponse.getAllHeaders());

        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            return result;
        }

        try {
            String data = EntityUtils.toString(httpEntity, (charset != null) ? charset : DEFAULT_CHARSET);
            result.setData(data);
            return result;
        } finally {
            EntityUtils.consume(httpEntity);
        }
    }

}
