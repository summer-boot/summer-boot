package io.github.summer.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨域配置
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    /**
     * 启用？
     */
    private Boolean enable;

    /**
     * 路由匹配模式
     * 如：/**
     */
    private String pathPattern;

    /**
     * 允许的Header列表
     * 如：*
     */
    private List<String> allowHeaders = new ArrayList<>();

    /**
     * 允许的Method列表
     * 如：*, HEAD, GET, POST, PUT, DELETE, OPTIONS
     */
    private List<String> allowMethods = new ArrayList<>();

    /**
     * 允许的URI列表
     * 如：*
     */
    private List<String> allowOriginPatterns = new ArrayList<>();

    /**
     * 允许Cookie？
     */
    private Boolean allowCredentials;

    /**
     * 预请求生效时间，单位：秒
     * 如：1800L
     */
    private Long maxAge;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public List<String> getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(List<String> allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public List<String> getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(List<String> allowMethods) {
        this.allowMethods = allowMethods;
    }

    public List<String> getAllowOriginPatterns() {
        return allowOriginPatterns;
    }

    public void setAllowOriginPatterns(List<String> allowOriginPatterns) {
        this.allowOriginPatterns = allowOriginPatterns;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

}
