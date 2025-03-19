package io.github.summer.boot.xrepository.sharding;

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
     * @param tableSizeMask 表数掩码
     * @param hashCode      哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSizeMask, int hashCode) {
        return hashCode & tableSizeMask;
    }

    /**
     * 计算分表
     *
     * @param tableSizeMask 表数掩码
     * @param hashCode      哈希码
     * @return 第 n 表
     */
    public static int calculate(int tableSizeMask, long hashCode) {
        return (int) (hashCode & tableSizeMask);
    }

}
