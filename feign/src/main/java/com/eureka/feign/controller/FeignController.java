package com.eureka.feign.controller;

import com.eureka.feign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    FeignService feignService;

    @GetMapping(value = "/helloFeign")
    public String sayHi(@RequestParam String name) {
        return feignService.helloFeign(name);
    }


}
