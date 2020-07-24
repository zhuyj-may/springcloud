package com.eureka.feign.service.impl;

import com.eureka.feign.service.FeignService;
import org.springframework.stereotype.Component;

@Component
public class FeignServiceImpl implements FeignService {
    @Override
    public String helloFeign(String name) {
        return "sorry!" + name;
    }
}
