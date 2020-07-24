package com.eureka.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign是一个声明式的伪Http客户端，它使Http客户端变得更简单
 * 使用注解@FeignClient
 * Feign 采用的是基于接口的注解
 * Feign 整合了ribbon，具有负载均衡的能力
 * 整合了Hystrix，具有熔断的能力
 */
@FeignClient(value = "eurka-client-one")
public interface FeignService {

    /**
     * value = "/index"  需要保持跟服务提供者调用方法名称一样
     * @param name
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    String helloFeign(@RequestParam(value = "name") String name);
}
