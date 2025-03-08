package io.github.summer.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 本地缓存
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "caffeine")
public class CaffeineProperties {
    /**
     * [ 名称 : 配置 ]
     */
    private Map<String, String> spec;

    public Map<String, String> getSpec() {
        return spec;
    }

    public void setSpec(Map<String, String> spec) {
        this.spec = spec;
    }

}
