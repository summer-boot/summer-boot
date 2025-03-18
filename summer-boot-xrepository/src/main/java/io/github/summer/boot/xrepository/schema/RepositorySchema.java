package io.github.summer.boot.xrepository.schema;

import io.github.summer.boot.xrepository.JsonParser;
import jakarta.validation.constraints.NotNull;

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
    private String tableName = "";

    /**
     * 键名
     */
    private String keyName = "";

    /**
     * 分表表数
     */
    private int tableSize = 0;

    /**
     * 分表表数掩码
     */
    private int tableSizeMask = 0;

    /**
     * 分表表数，符合规范？
     */
    private boolean tableSizePower2 = false;

    /**
     * 缓存秒数
     */
    private int cacheTime = 0;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    @NotNull
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName != null ? tableName.trim() : "";
    }

    @NotNull
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName != null ? keyName.trim() : "";
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = Math.max(tableSize, 0);
    }

    public int getTableSizeMask() {
        return tableSizeMask;
    }

    public void setTableSizeMask(int tableSizeMask) {
        this.tableSizeMask = Math.max(tableSizeMask, 0);
    }

    public boolean isTableSizePower2() {
        return tableSizePower2;
    }

    public void setTableSizePower2(boolean tableSizePower2) {
        this.tableSizePower2 = tableSizePower2;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = Math.max(cacheTime, 0);
    }

}
