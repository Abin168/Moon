package com.moon.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MoonConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoonConsumerApplication.class, args);
    }

}
