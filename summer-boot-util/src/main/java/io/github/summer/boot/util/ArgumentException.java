package io.github.summer.boot.util;

import io.github.summer.boot.base.Code;
import io.github.summer.boot.base.Errors;
import io.github.summer.boot.base.IResult;

/**
 * 参数异常
 *
 * @author changebooks@qq.com
 */
public class ArgumentException extends IllegalArgumentException {
    /**
     * 错误码
     */
    private final int code;

    public ArgumentException(Throwable cause) {
        super(cause);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public ArgumentException(String message) {
        super(message);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public ArgumentException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ArgumentException(IResult result) {
        super(result.getMessage());
        this.code = result.getCode();
    }

    public ArgumentException(Errors err) {
        super(err.getMessage());
        this.code = err.getCode();
    }

    public int getCode() {
        return code;
    }

}
