package com.controller;

import com.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    ProductServerClient productServerClient;

    @FeignClient
    static interface ProductServerClient{
        @GetMapping("/{id}")
        public User findUserById(@PathVariable("id") Long id);
    }

    @GetMapping("/findUserById/{id}")
    public User findUserById(@PathVariable("id") Long id){
        return productServerClient.findUserById(id);
    }

    @Autowired
    RestTemplate restTemplate; //spring 提供的RestTemplate,方便调用Rest接口
    @Qualifier("eurekaClient")
    @Autowired
    EurekaClient eurekaClient;
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("product-server",false);
        Map urlVariables = new HashMap();
        urlVariables.put("hostName",instanceInfo.getHostName());
        urlVariables.put("port",instanceInfo.getPort());
        User user = restTemplate.getForObject("http://{hostName}:{port}/" + id, User.class,urlVariables);
        return user;
    }
}
