package io.github.summer.boot.xrepository;

import io.github.summer.boot.value.Value;
import io.github.summer.boot.xrepository.schema.RepositorySchema;
import io.github.summer.boot.xrepository.schema.RepositorySchemaRegistry;
import io.github.summer.boot.xrepository.sharding.ShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository Sharding
 *
 * @author changebooks@qq.com
 */
public final class RepositorySharding {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositorySharding.class);

    public RepositorySharding() {
    }

    /**
     * 计算分表
     *
     * @param tableName Table Name
     * @param value     the {@link Value} instance
     * @return 第 n 表, if null no sharding
     */
    public static Integer calculate(String tableName, Value value) {
        int tableSize = getTableSize(tableName);
        if (tableSize > 1) {
            return ShardingValue.calculate(tableSize, value);
        } else {
            return null;
        }
    }

    /**
     * 获取分表表数
     *
     * @param tableName Table Name
     * @return 分表表数
     */
    static int getTableSize(String tableName) {
        if (tableName == null) {
            LOGGER.error("getTableSize failed, tableName must not be null");
            return 0;
        }

        RepositorySchema repositorySchema = RepositorySchemaRegistry.get(tableName);
        if (repositorySchema != null) {
            return repositorySchema.getTableSize();
        } else {
            return 0;
        }
    }

}
