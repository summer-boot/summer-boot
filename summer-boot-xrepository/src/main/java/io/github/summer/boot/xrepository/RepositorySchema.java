package io.github.summer.boot.xrepository;

import java.io.Serializable;

/**
 * 数仓概要
 *
 * @author changebooks@qq.com
 */
public final class RepositorySchema implements Serializable {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 键名
     */
    private String keyName;

    /**
     * 分表表数
     */
    private int tableSize;

    /**
     * 缓存秒数
     */
    private int cacheTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }

}
