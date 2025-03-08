package io.github.summer.boot.util;

/**
 * 主键不存在
 *
 * @author changebooks@qq.com
 */
public class IdUnsupportedException extends RuntimeException {
    /**
     * Message
     */
    private static final String MESSAGE = "unsupported id";

    /**
     * Message Format
     */
    private static final String MESSAGE_FORMAT = "unsupported id: %d";

    public IdUnsupportedException() {
        super(MESSAGE);
    }

    public IdUnsupportedException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public IdUnsupportedException(Long id) {
        super(String.format(MESSAGE_FORMAT, id));
    }

    public IdUnsupportedException(Long id, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, id), cause);
    }

}
