package io.github.summer.boot.elastic;

import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;

/**
 * 拦截ES请求和ES响应，打日志
 *
 * @author changebooks@qq.com
 */
public class ElasticLogCustomizer implements RestClientBuilderCustomizer {

    @Override
    public void customize(RestClientBuilder builder) {
    }

    @Override
    public void customize(HttpAsyncClientBuilder builder) {
        ElasticLogReqInterceptor reqInterceptor = reqInterceptor();
        if (reqInterceptor != null) {
            builder.addInterceptorLast(reqInterceptor);
        }

        ElasticLogRespInterceptor respInterceptor = respInterceptor();
        if (respInterceptor != null) {
            builder.addInterceptorLast(respInterceptor);
        }
    }

    /**
     * 拦截ES请求
     *
     * @return {@link ElasticLogReqInterceptor} 实例
     */
    public ElasticLogReqInterceptor reqInterceptor() {
        return new ElasticLogReqInterceptor();
    }

    /**
     * 拦截ES响应
     *
     * @return {@link ElasticLogRespInterceptor} 实例
     */
    public ElasticLogRespInterceptor respInterceptor() {
        return new ElasticLogRespInterceptor();
    }

}
