package io.github.summer.boot.web;

import io.github.summer.boot.base.Result;
import io.github.summer.boot.logger.LogId;
import io.github.summer.boot.util.ResultUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 规范响应结果
 *
 * @author changebooks@qq.com
 */
public class ResponseBodySupport implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !unsupportedController(returnType);
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return ResultUtils.toSuccess(null);
        }

        if (body instanceof Result<?> result) {
            return processResult(result, returnType, selectedContentType, selectedConverterType, request, response);
        } else {
            return processObject(body, returnType, selectedContentType, selectedConverterType, request, response);
        }
    }

    /**
     * 规范响应结果
     *
     * @param data                  处理前的响应结果
     * @param returnType            结果类型
     * @param selectedContentType   内容类型
     * @param selectedConverterType 转换器类型
     * @param request               当前请求
     * @param response              当前响应
     * @return 处理后的响应结果
     */
    public Object processObject(Object data, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        return data;
    }

    /**
     * 规范响应结果
     *
     * @param result                处理前的响应结果
     * @param returnType            结果类型
     * @param selectedContentType   内容类型
     * @param selectedConverterType 转换器类型
     * @param request               当前请求
     * @param response              当前响应
     * @return 处理后的响应结果
     */
    public Object processResult(Result<?> result, MethodParameter returnType, MediaType selectedContentType,
                                Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                ServerHttpRequest request, ServerHttpResponse response) {
        appendLogId(result);
        return result;
    }

    /**
     * 忽略的控制器？
     *
     * @param returnType 结果类型
     * @return 忽略？
     */
    public boolean unsupportedController(MethodParameter returnType) {
        return false;
    }

    /**
     * Append Result.Log.id
     *
     * @param result the dto
     * @param <T>    the type of the desired data
     */
    private <T> void appendLogId(Result<T> result) {
        if (result == null) {
            return;
        }

        String logId = ResultUtils.getLogId(result);
        if (logId == null) {
            logId = LogId.get();
            ResultUtils.setLogId(result, logId);
        }
    }

}
