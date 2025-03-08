package io.github.summer.boot.util;

import io.github.summer.boot.base.Code;
import io.github.summer.boot.base.Errors;
import io.github.summer.boot.base.IResult;

/**
 * 数据库异常
 *
 * @author changebooks@qq.com
 */
public class DatabaseException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public DatabaseException(Throwable cause) {
        super(cause);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public DatabaseException(String message) {
        super(message);
        this.code = Code.SYSTEM_RUN_ERR;
    }

    public DatabaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DatabaseException(IResult result) {
        super(result.getMessage());
        this.code = result.getCode();
    }

    public DatabaseException(Errors err) {
        super(err.getMessage());
        this.code = err.getCode();
    }

    /**
     * 查询失败
     *
     * @return DatabaseException
     */
    public static DatabaseException newFindException() {
        return new DatabaseException(Errors.FIND_ERR);
    }

    /**
     * 新增失败
     *
     * @return DatabaseException
     */
    public static DatabaseException newInsertException() {
        return new DatabaseException(Errors.INSERT_ERR);
    }

    /**
     * 修改失败
     *
     * @return DatabaseException
     */
    public static DatabaseException newUpdateException() {
        return new DatabaseException(Errors.UPDATE_ERR);
    }

    /**
     * 删除失败
     *
     * @return DatabaseException
     */
    public static DatabaseException newDeleteException() {
        return new DatabaseException(Errors.DELETE_ERR);
    }

    public int getCode() {
        return code;
    }

}
