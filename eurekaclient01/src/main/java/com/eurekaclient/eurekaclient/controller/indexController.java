package com.eurekaclient.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class indexController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/index")
    public String index(@RequestParam(value = "name", defaultValue = "world") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
