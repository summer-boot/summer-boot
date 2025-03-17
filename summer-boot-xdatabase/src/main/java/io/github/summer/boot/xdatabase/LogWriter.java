package io.github.summer.boot.xdatabase;

import io.github.summer.boot.xdatabase.logger.LogDatabase;
import io.github.summer.boot.xdatabase.logger.LogExecutor;
import io.github.summer.boot.xdatabase.logger.LogTemplate;
import jakarta.annotation.Nullable;

/**
 * 写日志
 *
 * @author changebooks@qq.com
 */
public final class LogWriter {
    /**
     * the {@link LogDatabase} instance
     */
    private static LogDatabase database;

    /**
     * the {@link LogTemplate} instance
     */
    private static LogTemplate template;

    /**
     * the {@link LogExecutor} instance
     */
    private static LogExecutor executor;

    private LogWriter() {
    }

    @Nullable
    public static LogDatabase getDatabase() {
        return database;
    }

    public synchronized static void setDatabase(LogDatabase database) {
        LogWriter.database = database;
    }

    @Nullable
    public static LogTemplate getTemplate() {
        return template;
    }

    public synchronized static void setTemplate(LogTemplate template) {
        LogWriter.template = template;
    }

    @Nullable
    public static LogExecutor getExecutor() {
        return executor;
    }

    public synchronized static void setExecutor(LogExecutor executor) {
        LogWriter.executor = executor;
    }

}
