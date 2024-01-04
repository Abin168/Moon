package com.moon.cloud.nacos.config;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@RefreshScope
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "moon.group.isolation")
@AutoConfigureBefore({NacosDiscoveryAutoConfiguration.class})
public class NacosAllGroupConfig {
    private Set<String> groupNames;
    private String shareGroup;
    private String open;
}
