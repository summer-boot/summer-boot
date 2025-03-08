package io.github.summer.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 预热配置
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "warm-up")
public class WarmUpProperties {
    /**
     * 请求地址
     */
    private String url;

    /**
     * 预热时长，单位：秒
     */
    private Integer timeout;

    /**
     * 阻塞？
     */
    private Boolean blocking;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Boolean getBlocking() {
        return blocking;
    }

    public void setBlocking(Boolean blocking) {
        this.blocking = blocking;
    }

}
