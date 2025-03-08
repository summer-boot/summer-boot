package io.github.summer.boot.value;

/**
 * 类型不存在
 *
 * @author changebooks@qq.com
 */
public class UnsupportedValueTypeException extends RuntimeException {
    /**
     * Message
     */
    private static final String MESSAGE = "unsupported value type";

    /**
     * Message Format
     */
    private static final String MESSAGE_FORMAT = "unsupported value type: %d, name: %s";

    public UnsupportedValueTypeException() {
        super(MESSAGE);
    }

    public UnsupportedValueTypeException(Throwable cause) {
        super(MESSAGE, cause);
    }

    public UnsupportedValueTypeException(Integer valueType) {
        super(String.format(MESSAGE_FORMAT, valueType, ""));
    }

    public UnsupportedValueTypeException(Integer valueType, String valueName) {
        super(String.format(MESSAGE_FORMAT, valueType, valueName));
    }

    public UnsupportedValueTypeException(Integer valueType, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, valueType, ""), cause);
    }

    public UnsupportedValueTypeException(Integer valueType, String valueName, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, valueType, valueName), cause);
    }

}
