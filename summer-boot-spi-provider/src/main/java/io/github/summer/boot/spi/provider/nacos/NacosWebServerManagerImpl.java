package io.github.summer.boot.spi.provider.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import io.github.summer.boot.spi.provider.WebServerManager;
import io.github.summer.boot.spi.provider.WebServerSchema;

/**
 * @author changebooks@qq.com
 */
public class NacosWebServerManagerImpl implements WebServerManager {
    /**
     * 上线和下线标识
     */
    private static final String STATUS_UP = "UP";
    private static final String STATUS_DOWN = "DOWN";

    /**
     * 管理 {@link NacosServiceRegistry} 生命周期
     */
    private final NacosAutoServiceRegistration autoServiceRegistration;

    /**
     * 服务管理
     */
    private final NacosServiceRegistry serviceRegistry;

    /**
     * 服务配置
     */
    private final NacosRegistration registration;

    /**
     * 服务端口
     */
    private int webPort;

    public NacosWebServerManagerImpl(NacosAutoServiceRegistration autoServiceRegistration,
                                     NacosServiceRegistry serviceRegistry,
                                     NacosRegistration registration) {
        this.autoServiceRegistration = autoServiceRegistration;
        this.serviceRegistry = serviceRegistry;
        this.registration = registration;
    }

    @Override
    public boolean onWebInitialized(int webPort) {
        this.webPort = Math.max(webPort, 0);
        return true;
    }

    @Override
    public boolean register() {
        NacosDiscoveryProperties discoveryProperties = registration.getNacosDiscoveryProperties();
        discoveryProperties.setRegisterEnabled(true);

        autoServiceRegistration.start();
        return true;
    }

    @Override
    public boolean deregister() {
        NacosDiscoveryProperties discoveryProperties = registration.getNacosDiscoveryProperties();
        discoveryProperties.setRegisterEnabled(true);

        autoServiceRegistration.stop();
        return true;
    }

    @Override
    public boolean isRegister() {
        Object status = serviceRegistry.getStatus(registration);
        if (status != null) {
            return STATUS_UP.equals(status);
        } else {
            return false;
        }
    }

    @Override
    public boolean isDeregister() {
        Object status = serviceRegistry.getStatus(registration);
        if (status != null) {
            return STATUS_DOWN.equals(status);
        } else {
            return true;
        }
    }

    @Override
    public WebServerSchema newWebSchema() {
        WebServerSchema schema = new WebServerSchema();

        NacosDiscoveryProperties discoveryProperties = registration.getNacosDiscoveryProperties();

        String server = registration.getServiceId();
        schema.setServer(server);

        String host = registration.getHost();
        schema.setHost(host);

        int defaultPort = registration.getPort();
        int port = Math.max(this.webPort, defaultPort);
        schema.setPort(port);

        String group = discoveryProperties.getGroup();
        schema.setGroup(group);

        String cluster = registration.getCluster();
        schema.setCluster(cluster);

        return schema;
    }

}
