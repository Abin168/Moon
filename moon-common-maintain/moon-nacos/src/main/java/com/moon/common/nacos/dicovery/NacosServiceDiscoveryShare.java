package com.moon.common.nacos.dicovery;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.moon.common.nacos.config.NacosAllGroupConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class NacosServiceDiscoveryShare extends NacosServiceDiscovery {

    private NacosDiscoveryProperties discoveryProperties;
    private NacosServiceManager nacosServiceManager;
    private NacosAllGroupConfig nacosAllGroupConfig;

    public NacosServiceDiscoveryShare(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager,
                                      NacosAllGroupConfig nacosAllGroupConfig) {
        super(discoveryProperties, nacosServiceManager);
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.nacosAllGroupConfig = nacosAllGroupConfig;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        String group = discoveryProperties.getGroup();
        String shareGroup = nacosAllGroupConfig.getShareGroup();
        // fist get List<ServiceInstance> from origin group
        List<Instance> instanceList = namingService().selectInstances(serviceId, group, true);
        log.debug("NacosServiceDiscoveryShare origin instanceList={}", JSONObject.toJSONString(instanceList));
        if (CollectionUtils.isEmpty(instanceList)) {
            // second get List<ServiceInstance> from share group
            instanceList = namingService().selectInstances(serviceId, shareGroup, true);
            log.debug("NacosServiceDiscoveryShare share instanceList={}", JSONObject.toJSONString(instanceList));
        }
        return hostToServiceInstanceList(instanceList, serviceId);
    }

    @Override
    public List<String> getServices() throws NacosException {
        List<String> result = new ArrayList<>();
        Consumer<List<String>> consumer = result::addAll;
        String group = discoveryProperties.getGroup();
        String shareGroup = nacosAllGroupConfig.getShareGroup();
        ListView<String> origin = namingService().getServicesOfServer(1, Integer.MAX_VALUE, group);
        ListView<String> share = namingService().getServicesOfServer(1, Integer.MAX_VALUE, shareGroup);
        if (Objects.nonNull(origin)) {
            List<String> originService = origin.getData();
            log.debug("NacosServiceDiscoveryShare originService={}", JSONObject.toJSONString(originService));
            consumer.accept(originService);
        }
        if (Objects.nonNull(share)) {
            List<String> shareService = share.getData();
            log.debug("NacosServiceDiscoveryShare shareService={}", JSONObject.toJSONString(shareService));
            consumer.accept(shareService);
        }
        return result;
    }

    private NamingService namingService() {
        return nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
    }
}
