package io.github.summer.boot.xrepository.key;

import org.springframework.context.ApplicationEvent;

/**
 * 刷新键名配置
 *
 * @author changebooks@qq.com
 */
public class RefreshRepositoryKeyEvent extends ApplicationEvent {
    /**
     * Table Name = null ? Refresh All
     */
    private String tableName;

    public RefreshRepositoryKeyEvent() {
        super("-- Refresh All --");
    }

    public RefreshRepositoryKeyEvent(String tableName) {
        super(tableName);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
