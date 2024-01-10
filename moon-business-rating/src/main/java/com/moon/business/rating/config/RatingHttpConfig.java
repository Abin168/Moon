package com.moon.business.rating.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rating.http")
@Component
@RefreshScope
@Data
public class RatingHttpConfig {

    /**
     * http connect time out mill
     */
    private Integer connectTimeout;

    /**
     * http socket time out mill
     */
    private Integer socketTimeout;

    /**
     * http 从连接池获取链接的超时时间 单位：毫秒
     */
    private Integer connectionRequestTimeout;

    /**
     * http 连接池大小
     */
    private Integer maxTotal;

    /**
     * 每个路由最多支持的链接数
     */
    private Integer maxPerRoute;

    /**
     * 持久链接的存活时间 单位 s
     */
    private Integer timeToLive;

    /**
     * 等待 HttpClient 初始化完成的时间 单位 毫秒
     */
    private Integer waitHttClientInit;

}
