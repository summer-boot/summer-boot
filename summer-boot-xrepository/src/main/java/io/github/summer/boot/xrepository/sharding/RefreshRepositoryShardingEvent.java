package io.github.summer.boot.xrepository.sharding;

import org.springframework.context.ApplicationEvent;

/**
 * 刷新分表配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryShardingEvent extends ApplicationEvent {
    /**
     * Table Name = null ? Refresh All
     */
    private String tableName;

    public RefreshRepositoryShardingEvent() {
        super("-- Refresh All --");
    }

    public RefreshRepositoryShardingEvent(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
