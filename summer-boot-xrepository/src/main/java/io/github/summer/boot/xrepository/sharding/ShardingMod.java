package io.github.summer.boot.xrepository.sharding;

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
     * @param hashCode  哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, int hashCode) {
        return hashCode % tableSize;
    }

    /**
     * 计算分表
     *
     * @param tableSize 分表表数
     * @param hashCode  哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSize, long hashCode) {
        return (int) (hashCode % tableSize);
    }

}
