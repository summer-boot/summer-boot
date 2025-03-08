package io.github.summer.boot.elastic;

import io.github.summer.boot.base.Code;
import io.github.summer.boot.base.Errors;
import io.github.summer.boot.base.IResult;

/**
 * ES异常
 *
 * @author changebooks@qq.com
 */
public class ElasticException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public ElasticException(Throwable cause) {
        super(cause);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public ElasticException(String message) {
        super(message);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public ElasticException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ElasticException(IResult result) {
        super(result.getMessage());
        this.code = result.getCode();
    }

    public ElasticException(Errors err) {
        super(err.getMessage());
        this.code = err.getCode();
    }

    /**
     * 查询失败
     *
     * @return EsException
     */
    public static ElasticException newFindException() {
        return new ElasticException(Errors.FIND_ERR);
    }

    /**
     * 新增失败
     *
     * @return EsException
     */
    public static ElasticException newInsertException() {
        return new ElasticException(Errors.INSERT_ERR);
    }

    /**
     * 修改失败
     *
     * @return EsException
     */
    public static ElasticException newUpdateException() {
        return new ElasticException(Errors.UPDATE_ERR);
    }

    /**
     * 删除失败
     *
     * @return EsException
     */
    public static ElasticException newDeleteException() {
        return new ElasticException(Errors.DELETE_ERR);
    }

    public int getCode() {
        return code;
    }

}
