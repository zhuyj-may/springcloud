package com.eureka.eurekribbona.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    /*该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法
    * 在未加熔断器之前，eureka Server检测未续约客户端并从注册中心剔除
    * 需要一定时间，按照缺省60秒，两台客户端模拟集群，其中一台服务故障或者
    * 下线的看情况，在故障服务未被剔除出注册中心时，按照负载均衡策略默认轮询
    * 情况，请求50%命中故障\下线服务，造成浏览器端不好体验
    *
    * 熔断器的作用可使在其中一台服务故障/下线，或者网络原因、并发状态线程阻塞导致服务瘫痪，
    * 立刻通过fallbackMethod回调error函数，
    */
    @HystrixCommand(fallbackMethod = "helloError")
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


    public String helloError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
