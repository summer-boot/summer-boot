package io.github.summer.boot.spi.provider;

import io.github.summer.boot.base.JsonParser;

import java.io.Serializable;

/**
 * 服务概要
 *
 * @author changebooks@qq.com
 */
public final class WebServerSchema implements Serializable {
    /**
     * 服务名称
     */
    private String server;

    /**
     * 服务地址
     */
    private String host;

    /**
     * 服务端口
     */
    private int port;

    /**
     * 分组
     */
    private String group;

    /**
     * 集群
     */
    private String cluster;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

}
