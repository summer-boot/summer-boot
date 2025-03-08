package io.github.summer.boot.util;

/**
 * 分表下标不支持
 *
 * @author changebooks@qq.com
 */
public class ShardingIndexUnsupportedException extends RuntimeException {
    /**
     * Message Format
     */
    private static final String MESSAGE_FORMAT = "unsupported sharding index, table name: %s, table index: %s";

    public ShardingIndexUnsupportedException() {
    }

    public ShardingIndexUnsupportedException(Throwable cause) {
        super(cause);
    }

    public ShardingIndexUnsupportedException(String tableName, Integer tableIndex) {
        super(String.format(MESSAGE_FORMAT, tableName, tableIndex));
    }

    public ShardingIndexUnsupportedException(String tableName, Integer tableIndex, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, tableName, tableIndex), cause);
    }

}
