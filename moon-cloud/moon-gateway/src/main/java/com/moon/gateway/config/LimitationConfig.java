package com.moon.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * redis limit config
 */
@ConfigurationProperties(prefix = "redis.limit")
@Component
@RefreshScope
@Data
public class LimitationConfig {



}
