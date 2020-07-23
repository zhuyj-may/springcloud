package com.eureka.eurekribbona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费者
 * ribbon方式：ribbon是一个负载均衡客户端
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekribbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekribbonApplication.class, args);
    }

    //通过@LoadBalanced注解表明这个restRemplate开启负载均衡的功能
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
