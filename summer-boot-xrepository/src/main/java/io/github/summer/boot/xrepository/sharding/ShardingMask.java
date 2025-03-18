package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.value.Types;
import io.github.summer.boot.value.UnsupportedValueTypeException;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

/**
 * 计算分表，掩码
 *
 * @author changebooks@qq.com
 */
public final class ShardingMask {

    private ShardingMask() {
    }

    /**
     * 计算分表
     *
     * @param tableSizeMask 分表表数掩码
     * @param value         the {@link Value} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSizeMask, @NotNull Value value) {
        int type = value.getType();
        return switch (type) {
            case Types.STRING -> calculate(tableSizeMask, HashCode.hashCode(value.getValueString()));
            case Types.INTEGER -> calculate(tableSizeMask, HashCode.hashCode(value.getValueInteger()));
            case Types.LONG -> calculate(tableSizeMask, HashCode.hashCode(value.getValueLong()));
            case Types.BIG_DECIMAL -> calculate(tableSizeMask, HashCode.hashCode(value.getValueBigDecimal()));
            case Types.DATE -> calculate(tableSizeMask, HashCode.hashCode(value.getValueDate()));
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    /**
     * 计算分表
     *
     * @param tableSizeMask 分表表数掩码
     * @param hashedCode    哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSizeMask, int hashedCode) {
        return hashedCode & tableSizeMask;
    }

    /**
     * 计算分表
     *
     * @param tableSizeMask 分表表数掩码
     * @param hashedCode    哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSizeMask, long hashedCode) {
        return (int) (hashedCode & tableSizeMask);
    }

}
