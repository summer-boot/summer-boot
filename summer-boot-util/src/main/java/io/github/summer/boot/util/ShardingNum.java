package io.github.summer.boot.util;

import io.github.summer.boot.autoconfigure.ShardingNumProperties;
import io.github.summer.boot.base.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单库分表
 *
 * @author changebooks@qq.com
 */
public final class ShardingNum {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardingNum.class);

    /**
     * 表数，2的幂次方
     */
    private final int tableSize;

    /**
     * 表数掩码
     */
    private final int tableSizeMask;

    public ShardingNum(int tableSize) {
        AssertUtils.isPositive(tableSize, "tableSize");

        int tableSizeMask = tableSize - 1;
        Assert.checkArgument((tableSize & tableSizeMask) == 0, "tableSize must be power of 2");

        this.tableSize = tableSize;
        this.tableSizeMask = tableSizeMask;

        LOGGER.info("ShardingNum notice, tableSize: {}, tableSizeMask: {}", this.tableSize, this.tableSizeMask);
    }

    /**
     * 单库分表
     *
     * @param props {@link ShardingNumProperties} 配置
     * @return {@link ShardingNum} 实例
     */
    public static ShardingNum properties(ShardingNumProperties props) {
        Integer tableSize = props.getTableSize();
        AssertUtils.nonNull(tableSize, "tableSize");

        LOGGER.info("ShardingNum notice, tableSize: {}", tableSize);
        return new ShardingNum(tableSize);
    }

    /**
     * 计算分表，Key % 表数
     *
     * @param key hashed code
     * @return 第 n 表
     */
    public int getNum(int key) {
        return key & tableSizeMask;
    }

    /**
     * 计算分表，Key % 表数
     *
     * @param key hashed code
     * @return 第 n 表
     */
    public int getNum(long key) {
        return (int) (key & tableSizeMask);
    }

    public int getTableSize() {
        return tableSize;
    }

    public int getTableSizeMask() {
        return tableSizeMask;
    }

}
