package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.value.Types;
import io.github.summer.boot.value.UnsupportedValueTypeException;
import io.github.summer.boot.value.Value;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计算分表
 *
 * @author changebooks@qq.com
 */
public final class ShardingValue {

    private ShardingValue() {
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
            case Types.STRING -> calculate(tableSize, value.getValueString());
            case Types.INTEGER -> calculate(tableSize, value.getValueInteger());
            case Types.LONG -> calculate(tableSize, value.getValueLong());
            case Types.BIG_DECIMAL -> calculate(tableSize, value.getValueBigDecimal());
            case Types.DATE -> calculate(tableSize, value.getValueDate());
            default -> throw new UnsupportedValueTypeException(type);
        };
    }

    /**
     * String Value
     *
     * @param tableSize 分表表数
     * @param value     the {@link String} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, String value) {
        int hashCode = HashCode.hashCode(value);
        return calculate(tableSize, hashCode);
    }

    /**
     * Integer Value
     *
     * @param tableSize 分表表数
     * @param value     the {@link Integer} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, Integer value) {
        int hashCode = HashCode.hashCode(value);
        return calculate(tableSize, hashCode);
    }

    /**
     * Long Value
     *
     * @param tableSize 分表表数
     * @param value     the {@link Long} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, Long value) {
        long hashCode = HashCode.hashCode(value);
        return calculate(tableSize, hashCode);
    }

    /**
     * BigDecimal Value
     *
     * @param tableSize 分表表数
     * @param value     the {@link BigDecimal} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, BigDecimal value) {
        int hashCode = HashCode.hashCode(value);
        return calculate(tableSize, hashCode);
    }

    /**
     * Date Value
     *
     * @param tableSize 分表表数
     * @param value     the {@link Date} instance
     * @return 第 n 表
     */
    public static int calculate(int tableSize, Date value) {
        long hashCode = HashCode.hashCode(value);
        return calculate(tableSize, hashCode);
    }

    /**
     * 计算分表
     *
     * @param tableSize 分表表数
     * @param hashCode  哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, int hashCode) {
        int tableSizeMask = tableSize - 1;
        boolean isPower2 = (tableSize & tableSizeMask) == 0;
        if (isPower2) {
            return ShardingMask.calculate(tableSizeMask, hashCode);
        } else {
            return ShardingMod.calculate(tableSize, hashCode);
        }
    }

    /**
     * 计算分表
     *
     * @param tableSize 分表表数
     * @param hashCode  哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, long hashCode) {
        int tableSizeMask = tableSize - 1;
        boolean isPower2 = (tableSize & tableSizeMask) == 0;
        if (isPower2) {
            return ShardingMask.calculate(tableSizeMask, hashCode);
        } else {
            return ShardingMod.calculate(tableSize, hashCode);
        }
    }

}
