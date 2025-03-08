package io.github.summer.boot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * Data Source Name
 *
 * @author changebooks@qq.com
 */
public final class Dsn {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dsn.class);

    /**
     * 域名或Ip、数据库名、参数
     * jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true&allowMultiQueries=true
     */
    private final String url;

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    public Dsn(String url, String username, String password) {
        AssertUtils.nonEmpty(url, "url");
        AssertUtils.nonEmpty(username, "username");

        this.url = url;
        this.username = username;
        this.password = password;

        LOGGER.info("Dsn notice, url: {}, username: {}", url, username);
    }

    /**
     * 数据源
     *
     * @param props {@link DataSourceProperties} 配置
     * @return {@link Dsn} 实例
     */
    public static Dsn properties(DataSourceProperties props) {
        String url = props.getUrl();
        String username = props.getUsername();
        String password = props.getPassword();

        LOGGER.info("Dsn notice, url: {}, username: {}", url, username);
        return new Dsn(url, username, password);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
