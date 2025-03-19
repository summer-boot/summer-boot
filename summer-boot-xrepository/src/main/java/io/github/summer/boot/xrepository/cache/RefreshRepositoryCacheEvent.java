package io.github.summer.boot.xrepository.cache;

import org.springframework.context.ApplicationEvent;

/**
 * 刷新缓存配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryCacheEvent extends ApplicationEvent {
    /**
     * Table Name = null ? Refresh All
     */
    private String tableName;

    public RefreshRepositoryCacheEvent() {
        super("-- Refresh All --");
    }

    public RefreshRepositoryCacheEvent(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
