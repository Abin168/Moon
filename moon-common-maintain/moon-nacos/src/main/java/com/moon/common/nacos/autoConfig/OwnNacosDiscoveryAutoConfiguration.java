package com.moon.common.nacos.autoConfig;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.moon.common.nacos.config.NacosAllGroupConfig;
import com.moon.common.nacos.dicovery.NacosServiceDiscoveryAll;
import com.moon.common.nacos.dicovery.NacosServiceDiscoveryShare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({NacosDiscoveryAutoConfiguration.class})
public class OwnNacosDiscoveryAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public NacosDiscoveryProperties nacosProperties() {
        return new NacosDiscoveryProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "moon.group.isolation.open", havingValue = "true")
    public NacosServiceDiscovery nacosServiceDiscoveryAll(NacosDiscoveryProperties discoveryProperties,
                                                            NacosServiceManager nacosServiceManager,
                                                            NacosAllGroupConfig nacosAllGroupConfig) {
        return new NacosServiceDiscoveryAll(discoveryProperties, nacosServiceManager, nacosAllGroupConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "moon.group.isolation.open", matchIfMissing = true)
    public NacosServiceDiscovery nacosServiceDiscoveryShare(NacosDiscoveryProperties discoveryProperties,
                                                          NacosServiceManager nacosServiceManager,
                                                          NacosAllGroupConfig nacosAllGroupConfig) {
        return new NacosServiceDiscoveryShare(discoveryProperties, nacosServiceManager, nacosAllGroupConfig);
    }
}
