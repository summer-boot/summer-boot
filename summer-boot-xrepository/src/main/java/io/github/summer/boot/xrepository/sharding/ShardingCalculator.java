package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.schema.RepositorySchema;

/**
 * 计算分表
 *
 * @author changebooks@qq.com
 */
public final class ShardingCalculator {

    private ShardingCalculator() {
    }

    /**
     * 计算分表
     *
     * @param repositorySchema the {@link RepositorySchema} instance
     * @param value            the {@link Value} instance
     * @return 第 n 表, null - 不分表
     */
    public static Integer calculate(RepositorySchema repositorySchema, Value value) {
        if (repositorySchema == null) {
            return null;
        }

        if (value == null) {
            return null;
        }

        int tableSize = repositorySchema.getTableSize();
        if (tableSize <= 1) {
            return null;
        }

        int tableSizeMask = repositorySchema.getTableSizeMask();
        if (tableSizeMask <= 0) {
            return null;
        }

        boolean tableSizePower2 = repositorySchema.isTableSizePower2();
        if (tableSizePower2) {
            return ShardingMask.calculate(tableSizeMask, value);
        } else {
            return ShardingMod.calculate(tableSize, value);
        }
    }

}
