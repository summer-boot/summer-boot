package io.github.summer.boot.spi.subscriber;

import feign.Retryer;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;

/**
 * Service Provider Interface Configuration
 *
 * @author changebooks@qq.com
 */
public class SpiConfigurer {

    @Bean
    public Decoder decoder() {
        return new SpiDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public SpiTraceIdInterceptor spiTraceIdInterceptor() {
        return new SpiTraceIdInterceptor();
    }

    @Bean
    public SpiLogIdInterceptor spiLogIdInterceptor() {
        return new SpiLogIdInterceptor();
    }

}
