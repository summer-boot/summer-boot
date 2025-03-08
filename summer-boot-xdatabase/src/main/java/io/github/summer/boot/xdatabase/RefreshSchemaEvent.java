package io.github.summer.boot.xdatabase;

import org.springframework.context.ApplicationEvent;

/**
 * Refresh Schema Event
 *
 * @author changebooks@qq.com
 */
public class RefreshSchemaEvent extends ApplicationEvent {
    /**
     * Table Name = null ? Refresh All
     */
    private String tableName;

    public RefreshSchemaEvent() {
        super("-- Refresh All --");
    }

    public RefreshSchemaEvent(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
