package com.eureka.eurekribbona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloRibbonService {

    @Autowired
    private RestTemplate restTemplate;

    public String helloRibbon(String name) {
        return restTemplate.getForObject("http://eurka-client-one/index?name=" + name, String.class);
    }
}
