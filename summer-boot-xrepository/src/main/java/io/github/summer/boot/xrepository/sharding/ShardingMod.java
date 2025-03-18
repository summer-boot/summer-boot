package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.value.Types;
import io.github.summer.boot.value.UnsupportedValueTypeException;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

/**
 * 计算分表，取模
 *
 * @author changebooks@qq.com
 */
public final class ShardingMod {

    private ShardingMod() {
    }

    /**
     * 计算分表
     *
     * @param tableSize 分表表数
     * @param value     the {@link Value} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, @NotNull Value value) {
        int type = value.getType();
        return switch (type) {
            case Types.STRING -> calculate(tableSize, HashCode.hashCode(value.getValueString()));
            case Types.INTEGER -> calculate(tableSize, HashCode.hashCode(value.getValueInteger()));
            case Types.LONG -> calculate(tableSize, HashCode.hashCode(value.getValueLong()));
            case Types.BIG_DECIMAL -> calculate(tableSize, HashCode.hashCode(value.getValueBigDecimal()));
            case Types.DATE -> calculate(tableSize, HashCode.hashCode(value.getValueDate()));
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    /**
     * 计算分表
     *
     * @param tableSize  分表表数
     * @param hashedCode 哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, int hashedCode) {
        return hashedCode % tableSize;
    }

    /**
     * 计算分表
     *
     * @param tableSize  分表表数
     * @param hashedCode 哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, long hashedCode) {
        return (int) (hashedCode % tableSize);
    }

}
