package io.github.summer.boot.binlog;

import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.github.summer.boot.autoconfigure.DsnProperties;
import jakarta.validation.constraints.NotNull;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.Objects;
import java.util.Properties;

/**
 * Binary log Environment
 *
 * @author changebooks@qq.com
 */
public final class BinlogEnvironment<T> {
    /**
     * Default Locking Mode
     */
    private static final String LOCKING_MODE = "none";

    /**
     * Default Sink Parallelism
     */
    private static final int SINK_PARALLELISM = 1;

    /**
     * Data Source Name Properties
     */
    private final DsnProperties dsnProperties;

    /**
     * Debezium Schema Deserialization
     */
    private DebeziumDeserializationSchema<T> deserializer;

    /**
     * Debezium Properties
     */
    private Properties debeziumProperties;

    /**
     * Startup Options
     */
    private StartupOptions startupOptions;

    /**
     * Restart Strategy
     */
    private RestartStrategies.RestartStrategyConfiguration restartStrategy;

    /**
     * Watermark Strategy
     */
    private WatermarkStrategy<T> watermarkStrategy;

    /**
     * Sink Parallelism
     */
    private int sinkParallelism;

    /**
     * Sink Function
     */
    private SinkFunction<T> sinkFunction;

    public BinlogEnvironment(DsnProperties dsnProps) {
        Objects.requireNonNull(dsnProps, "dsnProps must not be null");

        this.dsnProperties = dsnProps;
    }

    public String getSourceName() {
        DsnProperties dsnProps = getDsnProperties();
        return dsnProps.getDatabase();
    }

    @NotNull
    public DsnProperties getDsnProperties() {
        return dsnProperties;
    }

    public DebeziumDeserializationSchema<T> getDeserializer() {
        return deserializer;
    }

    public BinlogEnvironment<T> setDeserializer(DebeziumDeserializationSchema<T> deserializer) {
        this.deserializer = deserializer;
        return this;
    }

    @NotNull
    public Properties getDebeziumProperties() {
        return debeziumProperties != null ? debeziumProperties : defaultDebeziumProperties();
    }

    public BinlogEnvironment<T> setDebeziumProperties(Properties debeziumProperties) {
        this.debeziumProperties = debeziumProperties;
        return this;
    }

    @NotNull
    public StartupOptions getStartupOptions() {
        return startupOptions != null ? startupOptions : StartupOptions.latest();
    }

    public BinlogEnvironment<T> setStartupOptions(StartupOptions startupOptions) {
        this.startupOptions = startupOptions;
        return this;
    }

    @NotNull
    public RestartStrategies.RestartStrategyConfiguration getRestartStrategy() {
        return restartStrategy != null ? restartStrategy : RestartStrategies.noRestart();
    }

    public BinlogEnvironment<T> setRestartStrategy(RestartStrategies.RestartStrategyConfiguration restartStrategy) {
        this.restartStrategy = restartStrategy;
        return this;
    }

    @NotNull
    public WatermarkStrategy<T> getWatermarkStrategy() {
        return watermarkStrategy != null ? watermarkStrategy : WatermarkStrategy.noWatermarks();
    }

    public BinlogEnvironment<T> setWatermarkStrategy(WatermarkStrategy<T> watermarkStrategy) {
        this.watermarkStrategy = watermarkStrategy;
        return this;
    }

    public int getSinkParallelism() {
        return sinkParallelism > 0 ? sinkParallelism : SINK_PARALLELISM;
    }

    public BinlogEnvironment<T> setSinkParallelism(int sinkParallelism) {
        this.sinkParallelism = sinkParallelism;
        return this;
    }

    @NotNull
    public SinkFunction<T> getSinkFunction() {
        return sinkFunction != null ? sinkFunction : new PrintSinkFunction<>();
    }

    public BinlogEnvironment<T> setSinkFunction(SinkFunction<T> sinkFunction) {
        this.sinkFunction = sinkFunction;
        return this;
    }

    /**
     * Default Debezium Properties
     *
     * @return the {@link Properties} instance
     */
    public Properties defaultDebeziumProperties() {
        Properties props = new Properties();

        props.setProperty("snapshot.locking.mode", LOCKING_MODE);
        return props;
    }

}
