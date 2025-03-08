package io.github.summer.boot.util;

import io.github.summer.boot.autoconfigure.ShardingProperties;
import io.github.summer.boot.base.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分库分表
 *
 * @author changebooks@qq.com
 */
public final class Sharding {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sharding.class);

    /**
     * 库数，2的幂次方
     */
    private final int dbSize;

    /**
     * 库数掩码
     */
    private final int dbSizeMask;

    /**
     * 每库表数，2的幂次方
     */
    private final int tableSize;

    /**
     * 每库表数掩码
     */
    private final int tableSizeMask;

    /**
     * 总表数
     */
    private final int totalTableSize;

    public Sharding(int dbSize, int tableSize) {
        AssertUtils.isPositive(dbSize, "dbSize");
        AssertUtils.isPositive(tableSize, "tableSize");

        int dbSizeMask = dbSize - 1;
        int tableSizeMask = tableSize - 1;

        Assert.checkArgument((dbSize & dbSizeMask) == 0, "dbSize must be power of 2");
        Assert.checkArgument((tableSize & tableSizeMask) == 0, "tableSize must be power of 2");

        this.dbSize = dbSize;
        this.tableSize = tableSize;
        this.dbSizeMask = dbSizeMask;
        this.tableSizeMask = tableSizeMask;
        this.totalTableSize = this.dbSize * this.tableSize;

        LOGGER.info("Sharding notice, dbSize: {}, tableSize: {}, totalTableSize: {}, dbSizeMask: {}, tableSizeMask: {}",
                this.dbSize, this.tableSize, this.totalTableSize, this.dbSizeMask, this.tableSizeMask);
    }

    /**
     * 分库分表
     *
     * @param props {@link ShardingProperties} 配置
     * @return {@link Sharding} 实例
     */
    public static Sharding properties(ShardingProperties props) {
        Integer dbSize = props.getDbSize();
        Integer tableSize = props.getTableSize();

        AssertUtils.nonNull(dbSize, "dbSize");
        AssertUtils.nonNull(tableSize, "tableSize");

        LOGGER.info("Sharding notice, dbSize: {}, tableSize: {}", dbSize, tableSize);
        return new Sharding(dbSize, tableSize);
    }

    /**
     * 计算分库，Key % 库数
     *
     * @param key hashed code
     * @return 第 n 库
     */
    public int getDbNum(int key) {
        return key & dbSizeMask;
    }

    /**
     * 计算分库，Key % 库数
     *
     * @param key hashed code
     * @return 第 n 库
     */
    public int getDbNum(long key) {
        return (int) (key & dbSizeMask);
    }

    /**
     * 计算分表，Key / 库数 % 每库表数
     *
     * @param key hashed code
     * @return 第 n 表
     */
    public int getTableNum(int key) {
        return (key / dbSize) & tableSizeMask;
    }

    /**
     * 计算分表，Key / 库数 % 每库表数
     *
     * @param key hashed code
     * @return 第 n 表
     */
    public int getTableNum(long key) {
        return (int) ((key / dbSize) & tableSizeMask);
    }

    public int getDbSize() {
        return dbSize;
    }

    public int getTableSize() {
        return tableSize;
    }

    public int getTotalTableSize() {
        return totalTableSize;
    }

}
