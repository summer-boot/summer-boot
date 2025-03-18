package io.github.summer.boot.xrepository.schema;

import org.springframework.context.ApplicationEvent;

/**
 * 刷数仓概要
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositorySchemaEvent extends ApplicationEvent {
    /**
     * Table Name = null ? Refresh All
     */
    private String tableName;

    public RefreshRepositorySchemaEvent() {
        super("-- Refresh All --");
    }

    public RefreshRepositorySchemaEvent(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
