package com.moon.cloud.config;

import com.moon.cloud.interceptor.FeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public FeignInterceptor initFeignInterceptor() {
        return new FeignInterceptor();
    }
}
