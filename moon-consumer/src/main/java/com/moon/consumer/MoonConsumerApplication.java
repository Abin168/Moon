package com.moon.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.moon.consumer.remote"})
@EnableDiscoveryClient
@SpringBootApplication
public class MoonConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoonConsumerApplication.class, args);
    }

}
