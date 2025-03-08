package io.github.summer.boot.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.RequestLine;

import java.util.Date;

/**
 * 请求结果
 *
 * @author changebooks@qq.com
 */
public final class Result {
    /**
     * 请求参数
     */
    private Request request;

    /**
     * 请求结果
     */
    private HttpResult response;

    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date done;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public HttpResult getResponse() {
        return response;
    }

    public void setResponse(HttpResult response) {
        this.response = response;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getDone() {
        return done;
    }

    public void setDone(Date done) {
        this.done = done;
    }

    /**
     * 执行时间，毫秒
     *
     * @return 耗时
     */
    public long getElapsed() {
        if (start == null || done == null) {
            return 0;
        } else {
            return done.getTime() - start.getTime();
        }
    }

    /**
     * 请求参数
     */
    public static final class Request {
        /**
         * URI, method, version, config
         */
        private RequestLine base;

        /**
         * 头信息
         */
        private Header[] headers;

        /**
         * 参数
         */
        private HttpEntity entity;

        public RequestLine getBase() {
            return base;
        }

        public void setBase(RequestLine base) {
            this.base = base;
        }

        public Header[] getHeaders() {
            return headers;
        }

        public void setHeaders(Header[] headers) {
            this.headers = headers;
        }

        public HttpEntity getEntity() {
            return entity;
        }

        public void setEntity(HttpEntity entity) {
            this.entity = entity;
        }

    }

}
