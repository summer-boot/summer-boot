package io.github.summer.boot.binlog;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import jakarta.validation.constraints.NotNull;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Binary log Execution
 *
 * @author changebooks@qq.com
 */
public class BinlogExecution {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinlogExecution.class);

    /**
     * Doing ?
     */
    private final AtomicBoolean executed = new AtomicBoolean(false);

    /**
     * Environment
     */
    private final BinlogEnvironment<String> environment;

    /**
     * Data Source Name
     */
    private final String sourceName;

    public BinlogExecution(BinlogEnvironment<String> environment) {
        Objects.requireNonNull(environment, "environment must not be null");

        String sourceName = environment.getSourceName();
        Objects.requireNonNull(sourceName, "sourceName must not be null");

        this.environment = environment;
        this.sourceName = sourceName;
    }

    /**
     * 监听日志流，执行一次
     *
     * @return 成功或忽略？
     */
    public synchronized Boolean executeOnce() {
        String sourceName = getSourceName();

        if (executed.get()) {
            LOGGER.warn("executeOnce doing, sourceName: {}", sourceName);
            return null;
        }

        if (executed.compareAndSet(false, true)) {
            LOGGER.info("executeOnce start, sourceName: {}", sourceName);
            boolean success = execute();
            LOGGER.info("executeOnce stop, sourceName: {}, success: {}", sourceName, success);
            return success;
        } else {
            LOGGER.warn("executeOnce doing, executed.compareAndSet false, " +
                    "sourceName: {}", sourceName);
            return null;
        }
    }

    /**
     * 监听日志流
     *
     * @return 成功？
     */
    public boolean execute() {
        BinlogEnvironment<String> environment = getEnvironment();
        String sourceName = getSourceName();

        try {
            MySqlSource<String> source = MySqlSourceUtils.newInstance(environment);

            RestartStrategies.RestartStrategyConfiguration restartStrategy = environment.getRestartStrategy();
            WatermarkStrategy<String> watermarkStrategy = environment.getWatermarkStrategy();
            int sinkParallelism = environment.getSinkParallelism();
            SinkFunction<String> sinkFunction = environment.getSinkFunction();

            StreamExecutionEnvironment executionEnv = StreamExecutionEnvironment.getExecutionEnvironment();
            executionEnv.setRestartStrategy(restartStrategy);

            executionEnv
                    .fromSource(source, watermarkStrategy, sourceName)
                    .addSink(sinkFunction)
                    .setParallelism(sinkParallelism);

            LOGGER.info("execute start, sourceName: {}", sourceName);

            JobExecutionResult result = executionEnv.execute();
            boolean success = result != null && result.isJobExecutionResult();

            LOGGER.info("execute stop, sourceName: {}, success: {}", sourceName, success);
            return success;
        } catch (Throwable ex) {
            LOGGER.error("execute failed, sourceName: {}, throwable: ", sourceName, ex);
            return false;
        }
    }

    @NotNull
    public BinlogEnvironment<String> getEnvironment() {
        return environment;
    }

    @NotNull
    public String getSourceName() {
        return sourceName;
    }

}
