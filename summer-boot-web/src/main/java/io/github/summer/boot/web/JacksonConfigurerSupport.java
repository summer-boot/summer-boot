package io.github.summer.boot.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Jackson配置
 *
 * @author changebooks@qq.com
 */
public class JacksonConfigurerSupport implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (converters == null) {
            return;
        }

        for (HttpMessageConverter<?> c : converters) {
            if (c == null) {
                continue;
            }

            if (c instanceof MappingJackson2HttpMessageConverter) {
                extendMessageConverter((MappingJackson2HttpMessageConverter) c);
            }
        }
    }

    /**
     * 扩展 MessageConverter
     *
     * @param converter {@link MappingJackson2HttpMessageConverter} instance
     */
    public void extendMessageConverter(MappingJackson2HttpMessageConverter converter) {
        ObjectMapper objectMapper = converter.getObjectMapper();
        extendObjectMapper(objectMapper);
    }

    /**
     * 扩展 ObjectMapper
     *
     * @param objectMapper {@link ObjectMapper} instance
     */
    public void extendObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(newSimpleModule());
    }

    /**
     * 避免 JavaScript long 丢失精度
     * Long.class to String
     * Long.TYPE  to String
     *
     * @return {@link SimpleModule} instance
     */
    public SimpleModule newSimpleModule() {
        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        return simpleModule;
    }

}
