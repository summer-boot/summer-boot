package io.github.summer.boot.filter;

/**
 * 条件不存在
 *
 * @author changebooks@qq.com
 */
public class UnsupportedFilterException extends RuntimeException {
    /**
     * Message
     */
    private static final String MESSAGE = "unsupported filter";

    /**
     * Message Format
     */
    private static final String MESSAGE_FORMAT = "unsupported filter, name: %s";

    public UnsupportedFilterException() {
        super(MESSAGE);
    }

    public UnsupportedFilterException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public UnsupportedFilterException(String name) {
        super(String.format(MESSAGE_FORMAT, name));
    }

    public UnsupportedFilterException(String name, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, name), cause);
    }

}
