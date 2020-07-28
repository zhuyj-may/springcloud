package com.eureka.eurekazuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关组件，提供了路由和Filter过滤功能
 * 统一了后端服务的访问
 * 使用场景：包括路由转发，认证授权，限流，降级，防止用户重复提交等功能
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class EurekazuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekazuulApplication.class, args);
    }

}
