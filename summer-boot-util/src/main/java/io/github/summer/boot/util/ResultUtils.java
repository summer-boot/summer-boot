package io.github.summer.boot.util;

import io.github.summer.boot.base.*;

/**
 * 构建 Data Transfer Object
 *
 * @author changebooks@qq.com
 */
public final class ResultUtils {

    private ResultUtils() {
    }

    /**
     * 正确？
     *
     * @param result Result
     * @param <T>    the type of the desired data
     * @return ok ?
     */
    public static <T> boolean isSuccess(Result<T> result) {
        int code = result.getCode();
        return code == Errors.SUCCESS.getCode();
    }

    /**
     * 错误？
     *
     * @param result Result
     * @param <T>    the type of the desired data
     * @return !ok ?
     */
    public static <T> boolean isError(Result<T> result) {
        int code = result.getCode();
        return code != Errors.SUCCESS.getCode();
    }

    /**
     * OK
     *
     * @param data the data of result
     * @param <T>  the type of the desired data
     * @return Result
     */
    public static <T> Result<T> toSuccess(T data) {
        Result<T> result = new Result<>();

        result.setCode(Errors.SUCCESS.getCode());
        result.setMessage(Errors.SUCCESS.getMessage());
        result.setData(data);

        return result;
    }

    /**
     * Data Transfer Object to Data Transfer Object
     *
     * @param parameter the before dto
     * @param <T>       the type of the after data
     * @param <S>       the type of the before data
     * @return the after dto
     */
    public static <T, S> Result<T> fromResult(Result<S> parameter) {
        Result<T> result = new Result<>();

        if (parameter != null) {
            result.setCode(parameter.getCode());
            result.setMessage(parameter.getMessage());
        }

        return result;
    }

    /**
     * NullPointerException to Data Transfer Object
     *
     * @param ex  the null pointer exception
     * @param <T> the type of the desired data
     * @return Result
     */
    public static <T> Result<T> fromException(NullPointerException ex) {
        Result<T> result = new Result<>();

        if (ex != null) {
            result.setCode(Errors.ARGS_ERR.getCode());
            result.setMessage(ex.getMessage());
        }

        return result;
    }

    /**
     * IllegalArgumentException to Data Transfer Object
     *
     * @param ex  the illegal argument exception
     * @param <T> the type of the desired data
     * @return Result
     */
    public static <T> Result<T> fromException(IllegalArgumentException ex) {
        Result<T> result = new Result<>();

        if (ex != null) {
            result.setCode(Errors.ARGS_ERR.getCode());
            result.setMessage(ex.getMessage());
        }

        return result;
    }

    /**
     * Throwable to Data Transfer Object
     *
     * @param ex  the throwable
     * @param <T> the type of the desired data
     * @return Result
     */
    public static <T> Result<T> fromThrowable(Throwable ex) {
        Result<T> result = new Result<>();

        if (ex != null) {
            result.setMessage(ex.getMessage());
        }

        return result;
    }

    /**
     * Error Message to Data Transfer Object
     *
     * @param message the error message
     * @param <T>     the type of the desired data
     * @return Result
     */
    public static <T> Result<T> fromMessage(String message) {
        return fromMessage(Code.SYSTEM_RUN_ERR, message);
    }

    /**
     * Error Code and Error Message to Data Transfer Object
     *
     * @param code    the error code
     * @param message the error message
     * @param <T>     the type of the desired data
     * @return Result
     */
    public static <T> Result<T> fromMessage(int code, String message) {
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMessage(message);

        return result;
    }

    /**
     * Data Transfer Interface to Data Transfer Object
     *
     * @param ri  the before dti
     * @param <T> the type of the desired data
     * @return the after dto
     */
    public static <T> Result<T> fromResult(IResult ri) {
        return fromResult(ri, null);
    }

    /**
     * Data Transfer Interface and Error Message to Data Transfer Object
     *
     * @param ri      the before dti
     * @param message the error message
     * @param <T>     the type of the desired data
     * @return the after dto
     */
    public static <T> Result<T> fromResult(IResult ri, String message) {
        Result<T> result = new Result<>();

        if (ri != null) {
            result.setCode(ri.getCode());
            result.setMessage(ri.getMessage());
        }

        if (message != null) {
            result.setMessage(message);
        }

        return result;
    }

    /**
     * Get Result.Log.id
     *
     * @param result the Result
     * @param <T>    the type of the desired data
     * @return the log id
     */
    public static <T> String getLogId(Result<T> result) {
        if (result == null) {
            return null;
        }

        Log log = result.getLog();
        if (log != null) {
            return log.getId();
        } else {
            return null;
        }
    }

    /**
     * Set Result.Log.id
     *
     * @param result the Result
     * @param logId  the log id
     * @param <T>    the type of the desired data
     */
    public static <T> void setLogId(Result<T> result, String logId) {
        if (result == null) {
            return;
        }

        Log log = result.getLog();
        if (log == null) {
            if (logId == null) {
                return;
            }

            log = new Log();
        }

        log.setId(logId);
        result.setLog(log);
    }

}
