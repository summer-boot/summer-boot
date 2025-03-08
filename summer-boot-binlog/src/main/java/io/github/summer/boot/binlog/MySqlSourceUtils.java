package io.github.summer.boot.binlog;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.source.MySqlSourceBuilder;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.github.summer.boot.autoconfigure.DsnProperties;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Binary log MySqlSource Utils
 *
 * @author changebooks@qq.com
 */
public final class MySqlSourceUtils {

    private MySqlSourceUtils() {
    }

    /**
     * new MySqlSource from Environment
     *
     * @param environment the {@link BinlogEnvironment} instance
     * @return the {@link MySqlSource} instance
     */
    public static MySqlSource<String> newInstance(BinlogEnvironment<String> environment) {
        Objects.requireNonNull(environment, "environment must not be null");

        DsnProperties dsnProps = environment.getDsnProperties();
        DebeziumDeserializationSchema<String> deserializer = environment.getDeserializer();
        if (deserializer == null) {
            deserializer = new BinlogDeserialization();
        }

        Properties dbzProps = environment.getDebeziumProperties();
        StartupOptions startupOpts = environment.getStartupOptions();

        MySqlSourceBuilder<String> builder = newBuilder(dsnProps, deserializer);
        return builder
                .debeziumProperties(dbzProps)
                .startupOptions(startupOpts)
                .build();
    }

    /**
     * new MySqlSourceBuilder from Properties
     *
     * @param props        the {@link DsnProperties} instance
     * @param deserializer the {@link DebeziumDeserializationSchema} instance
     * @return the {@link MySqlSourceBuilder} instance
     */
    public static <T> MySqlSourceBuilder<T> newBuilder(DsnProperties props,
                                                       DebeziumDeserializationSchema<T> deserializer) {
        Objects.requireNonNull(props, "props must not be null");
        Objects.requireNonNull(deserializer, "deserializer must not be null");

        String hostname = props.getHostname();
        Objects.requireNonNull(hostname, "hostname must not be null");

        Integer port = props.getPort();
        Objects.requireNonNull(port, "port must not be null");

        String username = props.getUsername();
        Objects.requireNonNull(username, "username must not be null");

        String password = props.getPassword();
        Objects.requireNonNull(password, "password must not be null");

        String database = props.getDatabase();
        Objects.requireNonNull(database, "database must not be null");

        List<String> monitoredTables = props.getMonitoredTables();
        String[] tableList = BinlogUtils.tableList(database, monitoredTables);
        Objects.requireNonNull(tableList, "tableList must not be null");

        String serverTimeZone = props.getServerTimeZone();

        MySqlSourceBuilder<T> result = new MySqlSourceBuilder<>();

        result.hostname(hostname)
                .port(port)
                .username(username)
                .password(password)
                .databaseList(database)
                .tableList(tableList)
                .serverTimeZone(serverTimeZone)
                .deserializer(deserializer);
        return result;
    }

}
