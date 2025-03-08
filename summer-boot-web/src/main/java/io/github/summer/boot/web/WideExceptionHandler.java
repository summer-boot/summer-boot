package io.github.summer.boot.web;

import io.github.summer.boot.base.Errors;
import io.github.summer.boot.base.Result;
import io.github.summer.boot.util.ArgumentException;
import io.github.summer.boot.util.DatabaseException;
import io.github.summer.boot.util.ResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一处理异常
 *
 * @author changebooks@qq.com
 */
public class WideExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WideExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleHttpRequestMethodNotSupportedException, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = Errors.METHOD_NOT_ALLOWED.getCode();
        String message = ex.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

    @ExceptionHandler(ArgumentException.class)
    @ResponseBody
    public Result<?> handleArgumentException(ArgumentException ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleArgumentException, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = ex.getCode();
        String message = ex.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result<?> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleIllegalArgumentException, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = Errors.ARGS_ERR.getCode();
        String message = ex.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleMethodArgumentNotValidException, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = Errors.ARGS_ERR.getCode();

        FieldError error = ex.getBindingResult().getFieldError();
        String message = error != null ? error.getDefaultMessage() : Errors.ARGS_ERR.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseBody
    public Result<?> handleDatabaseException(DatabaseException ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleDatabaseException, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = ex.getCode();
        String message = ex.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<?> handleThrowable(Throwable ex, HttpServletRequest request) {
        LOGGER.error("WideExceptionHandler handleThrowable, reqURI: {}, throwable: ", request.getRequestURI(), ex);

        int code = Errors.SYSTEM_RUN_ERR.getCode();
        String message = ex.getMessage();

        return ResultUtils.fromMessage(code, message);
    }

}
