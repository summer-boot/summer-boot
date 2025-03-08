package io.github.summer.boot.http;

import org.apache.http.Header;
import org.apache.http.HttpStatus;

import java.io.Serializable;

/**
 * Http请求结果
 *
 * @author changebooks@qq.com
 */
public final class HttpResult implements Serializable {
    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 头信息
     */
    private Header[] headers;

    /**
     * 内容
     */
    private String data;

    public boolean isOk() {
        return statusCode == HttpStatus.SC_OK;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
