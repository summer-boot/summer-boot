package io.github.summer.boot.web;

import io.github.summer.boot.vo.BaseRequest;
import io.github.summer.boot.vo.BaseRequestFormatter;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 规范请求参数
 *
 * @author changebooks@qq.com
 */
public class RequestBodySupport implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (body instanceof BaseRequest request) {
            return processParameter(request, inputMessage, parameter, targetType, converterType);
        }

        return body;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 规范请求参数
     *
     * @param request       处理前的请求参数
     * @param inputMessage  请求内容
     * @param parameter     方法参数
     * @param targetType    目标类型
     * @param converterType 转换器类型
     * @return 处理后的请求参数
     */
    public Object processParameter(BaseRequest request, HttpInputMessage inputMessage, MethodParameter parameter,
                                   Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        BaseRequestFormatter.format(request);
        return request;
    }

}
