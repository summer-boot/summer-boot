package io.github.summer.boot.web;

import io.github.summer.boot.autoconfigure.CorsProperties;
import io.github.summer.boot.base.Check;
import io.github.summer.boot.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cross Origin Resource Sharing
 *
 * @author changebooks@qq.com
 */
public class CorsConfigurerSupport implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsConfigurerSupport.class);

    /**
     * 默认配置
     */
    private static final boolean DEFAULT_ENABLE = false;
    private static final String DEFAULT_PATH_PATTERN = "/**";
    private static final String[] DEFAULT_ALLOW_HEADERS = {"*"};
    private static final String[] DEFAULT_ALLOW_METHODS = {"*"};
    private static final String[] DEFAULT_ALLOW_ORIGIN_PATTERNS = {"*"};
    private static final boolean DEFAULT_ALLOW_CREDENTIALS = false;
    private static final long DEFAULT_MAX_AGE = 1800L;

    /**
     * 启用？
     */
    private Boolean enable;

    /**
     * 路由匹配模式
     */
    private String pathPattern;

    /**
     * 允许的Header列表
     */
    private List<String> allowHeaders;

    /**
     * 允许的Method列表
     */
    private List<String> allowMethods;

    /**
     * 允许的URI列表
     */
    private List<String> allowOriginPatterns;

    /**
     * 允许Cookie？
     */
    private Boolean allowCredentials;

    /**
     * 预请求生效时间，单位：秒
     */
    private Long maxAge;

    /**
     * 设置跨域参数
     *
     * @param registry   {@link CorsRegistry} instance
     * @param properties {@link CorsProperties} instance
     */
    public void addMappings(CorsRegistry registry, CorsProperties properties) {
        initializeProperties(properties);

        boolean enable = enable();
        String pathPattern = pathPattern();
        String[] allowHeaders = allowHeaders();
        String[] allowMethods = allowMethods();
        String[] allowOriginPatterns = allowOriginPatterns();
        boolean allowCredentials = allowCredentials();
        long maxAge = maxAge();

        LOGGER.info("CorsConfigurerSupport addMappings, enable: {}, pathPattern: {}, allowHeaders: {}, allowMethods: {}, allowOriginPatterns: {}, allowCredentials: {}, maxAge: {}",
                enable, pathPattern, allowHeaders, allowMethods, allowOriginPatterns, allowCredentials, maxAge);

        if (!enable) {
            return;
        }

        CorsRegistration registration = registry.addMapping(pathPattern);

        registration.allowedHeaders(allowHeaders);
        registration.allowedMethods(allowMethods);
        registration.allowedOriginPatterns(allowOriginPatterns);
        registration.allowCredentials(allowCredentials);
        registration.maxAge(maxAge);
    }

    /**
     * 初始化配置
     *
     * @param properties {@link CorsProperties} instance
     */
    public void initializeProperties(CorsProperties properties) {
        beforeInitializeProperties(properties);

        Boolean enable = properties.getEnable();
        String pathPattern = properties.getPathPattern();
        List<String> allowHeaders = properties.getAllowHeaders();
        List<String> allowMethods = properties.getAllowMethods();
        List<String> allowOriginPatterns = properties.getAllowOriginPatterns();
        Boolean allowCredentials = properties.getAllowCredentials();
        Long maxAge = properties.getMaxAge();

        this.enable = enable;
        this.pathPattern = cleanProperty(pathPattern);
        this.allowHeaders = cleanProperties(allowHeaders);
        this.allowMethods = cleanProperties(allowMethods);
        this.allowOriginPatterns = cleanProperties(allowOriginPatterns);
        this.allowCredentials = allowCredentials;
        this.maxAge = maxAge;

        afterInitializeProperties();
    }

    /**
     * 初始化配置前执行
     *
     * @param properties {@link CorsProperties} instance
     */
    public void beforeInitializeProperties(CorsProperties properties) {
    }

    /**
     * 初始化配置后执行
     */
    public void afterInitializeProperties() {
    }

    /**
     * 启用？
     *
     * @return Enable, or Disable
     * @see #DEFAULT_ENABLE
     */
    public boolean enable() {
        return Check.isNull(enable) ? DEFAULT_ENABLE : enable;
    }

    /**
     * 路由匹配模式
     *
     * @return 路由正则，如，/admin/**
     * @see #DEFAULT_PATH_PATTERN
     */
    public String pathPattern() {
        return Check.isEmpty(pathPattern) ? DEFAULT_PATH_PATTERN : pathPattern;
    }

    /**
     * 允许的Header列表
     * Access-Control-Allow-Headers
     *
     * @return Cache-Control, Content-Language, Expires
     * @see #DEFAULT_ALLOW_HEADERS
     */
    public String[] allowHeaders() {
        return Check.isEmpty(allowHeaders) ? DEFAULT_ALLOW_HEADERS : allowHeaders.toArray(new String[0]);
    }

    /**
     * 允许的Method列表
     *
     * @return [ *, HEAD, GET, POST, PUT, DELETE, OPTIONS ]
     * @see #DEFAULT_ALLOW_METHODS
     */
    public String[] allowMethods() {
        return Check.isEmpty(allowMethods) ? DEFAULT_ALLOW_METHODS : allowMethods.toArray(new String[0]);
    }

    /**
     * 允许的URI列表
     *
     * @return URI正则，如，http://*.domain.com:[*]
     * @see #DEFAULT_ALLOW_ORIGIN_PATTERNS
     */
    public String[] allowOriginPatterns() {
        return Check.isEmpty(allowOriginPatterns) ? DEFAULT_ALLOW_ORIGIN_PATTERNS : allowOriginPatterns.toArray(new String[0]);
    }

    /**
     * 允许Cookie？
     * Access-Control-Allow-Credentials
     *
     * @return Allow, or Deny
     * @see #DEFAULT_ALLOW_CREDENTIALS
     */
    public boolean allowCredentials() {
        return Check.isNull(allowCredentials) ? DEFAULT_ALLOW_CREDENTIALS : allowCredentials;
    }

    /**
     * 预请求生效时间
     *
     * @return Clients cached's seconds
     * @see #DEFAULT_MAX_AGE
     */
    public long maxAge() {
        return Check.isNull(maxAge) ? DEFAULT_MAX_AGE : maxAge;
    }

    /**
     * 清理多个配置
     *
     * @param values 清理前的配置列表
     * @return 清理后的配置列表
     */
    public List<String> cleanProperties(List<String> values) {
        return Optional.ofNullable(values)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::cleanProperty)
                .filter(Check::nonEmpty)
                .collect(Collectors.toList());
    }

    /**
     * 清理一个配置
     *
     * @param value 清理前的配置值
     * @return 清理后的配置值
     */
    public String cleanProperty(String value) {
        return StringUtils.trimSpace(value);
    }

}
