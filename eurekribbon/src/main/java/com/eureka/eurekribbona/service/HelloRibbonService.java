package com.eureka.eurekribbona.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HelloRibbonService {
    Logger logger = LoggerFactory.getLogger(HelloRibbonService.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    private int count = 0;

    public String helloRibbon(String name) {
        /*原ribbon负载均衡策略默认为轮询，当模拟两台服务提供者做集群，
        其中一台故障后，restTemplate依然按照轮询分配请求，
        10-15（大概）秒后未监听到注册服务的心跳（指未续约的服务）则从注册中心剔除
        在这监听的10-15秒（或可手动设置该值），百分之50纪律命中故障服务*/

        /*以下可以自定义负载均衡策略*/
        ServiceInstance serviceInstance = null;
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("eurka-client-one");
        logger.info("服务提供客户端短实例数量：" + serviceInstances.size());
        if (serviceInstances.size() == 1) {
            serviceInstance = serviceInstances.get(0);
        } else {
            serviceInstance = serviceInstances.get(count);
            count++;
            if (count >= serviceInstances.size()) count = 0;
        }
        logger.info(String.valueOf(serviceInstance.getPort()));
        return restTemplate.getForObject("http://" + serviceInstance.getServiceId() + "/index?name=" + name, String.class);
    }
}
