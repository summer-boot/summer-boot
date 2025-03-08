package io.github.summer.boot.http;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 执行请求
 *
 * @author changebooks@qq.com
 */
public final class HttpRequest {

    private HttpRequest() {
    }

    /**
     * Send Http Get Request, Use {@link Constant#CHARSET}
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpGet    the {@link HttpGet} instance
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doGet(final CloseableHttpClient httpClient,
                               HttpGet httpGet) throws IOException {
        return doGet(httpClient, httpGet, Constant.CHARSET);
    }

    /**
     * Send Http Get Request
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpGet    the {@link HttpGet} instance
     * @param charset    Charset, Default: {@link Constant#CHARSET}
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doGet(final CloseableHttpClient httpClient,
                               HttpGet httpGet,
                               final Charset charset) throws IOException {
        return execute(httpClient, httpGet, charset);
    }

    /**
     * Send Http Post Request, Use {@link Constant#CHARSET}
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpPost   the {@link HttpPost} instance
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doPost(final CloseableHttpClient httpClient,
                                HttpPost httpPost) throws IOException {
        return doPost(httpClient, httpPost, Constant.CHARSET);
    }

    /**
     * Send Http Post Request
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpPost   the {@link HttpPost} instance
     * @param charset    Charset, Default: {@link Constant#CHARSET}
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doPost(final CloseableHttpClient httpClient,
                                HttpPost httpPost,
                                final Charset charset) throws IOException {
        Result result = execute(httpClient, httpPost, charset);

        Result.Request request = result.getRequest();
        request.setEntity(httpPost.getEntity());

        result.setRequest(request);
        return result;
    }

    /**
     * Send Http Put Request, Use {@link Constant#CHARSET}
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpPut    the {@link HttpPut} instance
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doPut(final CloseableHttpClient httpClient,
                               HttpPut httpPut) throws IOException {
        return doPut(httpClient, httpPut, Constant.CHARSET);
    }

    /**
     * Send Http Put Request
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpPut    the {@link HttpPut} instance
     * @param charset    Charset, Default: {@link Constant#CHARSET}
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doPut(final CloseableHttpClient httpClient,
                               HttpPut httpPut,
                               final Charset charset) throws IOException {
        Result result = execute(httpClient, httpPut, charset);

        Result.Request request = result.getRequest();
        request.setEntity(httpPut.getEntity());

        result.setRequest(request);
        return result;
    }

    /**
     * Send Http Delete Request, Use {@link Constant#CHARSET}
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpDelete the {@link HttpDelete} instance
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doDelete(final CloseableHttpClient httpClient,
                                  HttpDelete httpDelete) throws IOException {
        return doDelete(httpClient, httpDelete, Constant.CHARSET);
    }

    /**
     * Send Http Delete Request
     *
     * @param httpClient HttpClientBuilder.create().build();
     * @param httpDelete the {@link HttpDelete} instance
     * @param charset    Charset, Default: {@link Constant#CHARSET}
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result doDelete(final CloseableHttpClient httpClient,
                                  HttpDelete httpDelete,
                                  final Charset charset) throws IOException {
        return execute(httpClient, httpDelete, charset);
    }

    /**
     * Send Http Request
     *
     * @param httpClient  HttpClientBuilder.create().build();
     * @param httpRequest HttpGet, HttpPost, HttpPut, HttpDelete
     * @param charset     Charset, Default: {@link Constant#CHARSET}
     * @return http.Result
     * @throws IOException if an error occurs reading the input stream
     */
    public static Result execute(final CloseableHttpClient httpClient,
                                 HttpRequestBase httpRequest,
                                 final Charset charset) throws IOException {
        Date start = new Date();

        HttpResult response = HttpUtils.execute(httpClient, httpRequest, charset);

        Date done = new Date();

        Result.Request request = new Result.Request();
        request.setBase(httpRequest.getRequestLine());
        request.setHeaders(httpRequest.getAllHeaders());

        Result result = new Result();
        result.setRequest(request);
        result.setResponse(response);
        result.setStart(start);
        result.setDone(done);

        return result;
    }

}
