package io.github.summer.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 数据源
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "dsn")
public class DsnProperties {
    /**
     * 域名或Ip
     */
    private String hostname;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 库名
     */
    private String database;

    /**
     * 监控表名
     */
    private List<String> monitoredTables;

    /**
     * 时区
     */
    private String serverTimeZone;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<String> getMonitoredTables() {
        return monitoredTables;
    }

    public void setMonitoredTables(List<String> monitoredTables) {
        this.monitoredTables = monitoredTables;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

}
