package io.github.summer.boot.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.summer.boot.autoconfigure.CaffeineProperties;
import io.github.summer.boot.base.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地缓存
 *
 * @author changebooks@qq.com
 */
public class CaffeineBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaffeineBuilder.class);

    /**
     * [ 名称 : 配置 ]
     */
    private final Map<String, String> specMap = new HashMap<>();

    public CaffeineBuilder(Map<String, String> specMap) {
        AssertUtils.nonNull(specMap, "specMap");

        for (Map.Entry<String, String> entry : specMap.entrySet()) {
            if (entry == null) {
                LOGGER.error("CaffeineBuilder failed, entry must not be null, specMap: {}", specMap);
                continue;
            }

            String key = entry.getKey();
            if (key == null) {
                LOGGER.error("CaffeineBuilder failed, key must not be null, specMap: {}", specMap);
                continue;
            }

            String value = entry.getValue();
            if (value == null) {
                LOGGER.error("CaffeineBuilder failed, value must not be null, specMap: {}", specMap);
                continue;
            }

            String name = key.trim();
            if (name.isEmpty()) {
                LOGGER.error("CaffeineBuilder failed, name must not be empty, specMap: {}", specMap);
                continue;
            }

            String spec = value.trim();
            if (spec.isEmpty()) {
                LOGGER.warn("CaffeineBuilder warning, spec is empty, specMap: {}", specMap);
            }

            this.specMap.put(name, spec);
        }

        LOGGER.info("CaffeineBuilder trace, specMap: {}", getSpecMap());
    }

    public static CaffeineBuilder properties(CaffeineProperties props) {
        AssertUtils.nonNull(props, "props");

        Map<String, String> spec = props.getSpec();
        return new CaffeineBuilder(spec);
    }

    /**
     * 创建 {@link Cache} 实例
     *
     * @param cacheName 名称
     * @return {@link Cache} 实例
     */
    public <K, V> Cache<K, V> build(String cacheName) {
        AssertUtils.nonEmpty(cacheName, "cacheName");

        Map<String, String> specMap = getSpecMap();
        String spec = specMap.get(cacheName);

        Assert.checkNonNull(spec, String.format("unsupported cacheName: %s", cacheName));

        Caffeine<Object, Object> caffeine = Caffeine.from(spec);
        return caffeine.build();
    }

    public Map<String, String> getSpecMap() {
        return specMap;
    }

}
