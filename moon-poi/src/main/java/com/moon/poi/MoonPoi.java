package com.moon.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MoonPoi {
    public static void main(String[] args) {
        SpringApplication.run(MoonPoi.class, args);
    }
}
