package com.exam.demo.crud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceClient {
    private final RestTemplate restTemplate;

    @Autowired
    public ServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Object callApi() {
        return restTemplate.getForObject("/users", Object.class);
    }
}
