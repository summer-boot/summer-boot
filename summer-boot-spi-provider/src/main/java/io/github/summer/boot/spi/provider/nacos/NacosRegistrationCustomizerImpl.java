package io.github.summer.boot.spi.provider.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosRegistrationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;

/**
 * @author changebooks@qq.com
 */
public class NacosRegistrationCustomizerImpl implements NacosRegistrationCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosRegistrationCustomizerImpl.class);

    /**
     * 默认阻止自动注册，建议手动注册
     */
    private static final boolean REGISTER = false;

    /**
     * 默认自动上线，注册后提供服务且接受客户端请求
     * spring-cloud-starter-alibaba-nacos-discovery 2021.0.5.0 版本，ONLINE = false 无效
     */
    private static final boolean ONLINE = true;

    @Override
    public void customize(NacosRegistration registration) {
        boolean register = isRegister();
        boolean online = isOnline();

        NacosDiscoveryProperties discoveryProperties = registration.getNacosDiscoveryProperties();
        boolean beforeRegister = discoveryProperties.isRegisterEnabled();
        boolean beforeOnline = discoveryProperties.isInstanceEnabled();

        discoveryProperties.setRegisterEnabled(register);
        discoveryProperties.setInstanceEnabled(online);

        boolean afterRegister = discoveryProperties.isRegisterEnabled();
        boolean afterOnline = discoveryProperties.isInstanceEnabled();

        LOGGER.info("NacosRegistrationCustomizerImpl notice, customize notice, " +
                        "register: {}, online: {}, beforeRegister: {}, beforeOnline: {}, afterRegister: {}, afterOnline: {}",
                register, online, beforeRegister, beforeOnline, afterRegister, afterOnline);
    }

    /**
     * 自动注册？
     * AutoServiceRegistrationProperties#isRegisterManagement = 是否注册
     * AutoServiceRegistrationProperties#isEnabled =
     * 注册前发通知 + AutoServiceRegistrationProperties#isRegisterManagement + 注册后发通知
     * alibaba-nacos-discovery 2021.0.5.0 NacosDiscoveryProperties#isRegisterEnabled =
     * AutoServiceRegistrationProperties#isEnabled + AutoServiceRegistrationProperties#isRegisterManagement
     *
     * @return True-自动注册、False-手动注册
     * @see AutoServiceRegistrationProperties#isRegisterManagement
     * @see AutoServiceRegistrationProperties#isEnabled
     * @see NacosDiscoveryProperties#isRegisterEnabled
     */
    public boolean isRegister() {
        return REGISTER;
    }

    /**
     * 自动上线？
     *
     * @return True-注册后提供服务且接受客户端请求、False-注册后不提供服务且不接受客户端请求-手动上线后提供服务
     * @see NacosDiscoveryProperties#isInstanceEnabled
     */
    public boolean isOnline() {
        return ONLINE;
    }

}
