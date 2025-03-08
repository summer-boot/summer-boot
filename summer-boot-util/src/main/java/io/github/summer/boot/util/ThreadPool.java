package io.github.summer.boot.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 标准线程池
 *
 * @author changebooks@qq.com
 */
public final class ThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPool.class);

    /**
     * 名称
     */
    private static final String NAME_FORMAT = "ThreadPool-%d";

    /**
     * 处理器数量，>= 1
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池核心线程数
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    /**
     * 线程池最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 多余的空闲线程生存时间
     */
    private static final long KEEP_ALIVE_TIME = 1L;

    /**
     * 阻塞队列
     */
    private static final BlockingQueue<Runnable> WORK_QUEUE = new SynchronousQueue<>();

    /**
     * 线程工厂
     */
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat(NAME_FORMAT).build();

    /**
     * 崩溃处理
     */
    private static final ThreadPoolExecutor.AbortPolicy ABORT_POLICY = new ThreadPoolExecutor.AbortPolicy();

    private static final ExecutorService EXECUTOR =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, WORK_QUEUE, THREAD_FACTORY, ABORT_POLICY);

    public static void execute(Runnable command) {
        AssertUtils.nonNull(command, "command");

        EXECUTOR.execute(() -> {
            try {
                command.run();
            } catch (Throwable ex) {
                LOGGER.error("execute failed, throwable: ", ex);
            }
        });
    }

}
