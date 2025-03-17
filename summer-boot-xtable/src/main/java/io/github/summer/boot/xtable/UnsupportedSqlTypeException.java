package io.github.summer.boot.xtable;

/**
 * 类型不存在
 *
 * @author changebooks@qq.com
 */
public class UnsupportedSqlTypeException extends RuntimeException {
    /**
     * Message
     */
    private static final String MESSAGE = "unsupported sql type";

    /**
     * Message Format
     */
    private static final String MESSAGE_FORMAT = "unsupported sql type: %d, column name: %s";

    public UnsupportedSqlTypeException() {
        super(MESSAGE);
    }

    public UnsupportedSqlTypeException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public UnsupportedSqlTypeException(Integer sqlType, String columnName) {
        super(String.format(MESSAGE_FORMAT, sqlType, columnName));
    }

    public UnsupportedSqlTypeException(Integer sqlType, String columnName, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, sqlType, columnName), cause);
    }

}
