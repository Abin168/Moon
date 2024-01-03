package com.moon.cloud.nacos.dicovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.moon.cloud.nacos.config.NacosAllGroupConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class NacosServiceDiscoveryAll extends NacosServiceDiscovery {

    private NacosDiscoveryProperties discoveryProperties;
    private NacosServiceManager nacosServiceManager;
    private NacosAllGroupConfig nacosAllGroupConfig;

    public NacosServiceDiscoveryAll(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager,
                                    NacosAllGroupConfig nacosAllGroupConfig) {
        super(discoveryProperties, nacosServiceManager);
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.nacosAllGroupConfig = nacosAllGroupConfig;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        Set<String> groupSet = nacosAllGroupConfig.getGroupNames();
        List<Instance> instanceList = new ArrayList<>();
        for (String group : groupSet) {
            instanceList = namingService().selectInstances(serviceId, group, true);
            if (CollectionUtils.isNotEmpty(instanceList)) {
                break;
            }
        }
        return hostToServiceInstanceList(instanceList, serviceId);
    }

    @Override
    public List<String> getServices() {
        List<String> result = new ArrayList<>();
        Set<String> groupSet = nacosAllGroupConfig.getGroupNames();
        groupSet.parallelStream().forEach(group -> {
            try {
                ListView<String> services = namingService().getServicesOfServer(1, Integer.MAX_VALUE, group);
                if (Objects.isNull(services) || CollectionUtils.isEmpty(services.getData())) {
                    return;
                }
                result.addAll(services.getData());
            } catch (NacosException e) {
                log.error("NacosServiceDiscoveryAll getServices error group={}", group, e);
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    private NamingService namingService() {
        return nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
    }
}
