package com.eureka.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigServer//开启配置服务器
@RestController
public class ConfigserverApplication {

    //测试服务是否能读取git  http://localhost:9001/foo/dev

    @GetMapping(value = "configTest")
    public String getConfigNum() {
        return "test";
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigserverApplication.class, args);
    }

}
