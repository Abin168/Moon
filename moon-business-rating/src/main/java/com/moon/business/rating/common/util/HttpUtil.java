package com.moon.business.rating.common.util;


import com.alibaba.fastjson.JSONObject;
import com.moon.business.rating.config.RatingHttpConfig;
import com.moon.core.common.constants.MoonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * HTTP 请求工具类
 */
@Slf4j
@Component
public class HttpUtil {
    @Autowired
    private RatingHttpConfig httpConfig;

    private static CloseableHttpClient httpClient;
    /**
     * Add countDownLatch to ensure httpClient is initialized
     */
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    log.info("HttpUtil httpClient close");
                    httpClient.close();
                } catch (IOException e) {
                    log.error("HttpUtil httpClient close error", e);
                }
            }
        });
    }

    @EventListener(classes = {ContextRefreshedEvent.class, RefreshScopeRefreshedEvent.class})
    public void init() {
        log.info("HttpUtil initConfig start={}", JSONObject.toJSONString(httpConfig));
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(httpConfig.getTimeToLive(), TimeUnit.SECONDS);
        connMgr.setMaxTotal(httpConfig.getMaxTotal());
        connMgr.setDefaultMaxPerRoute(httpConfig.getMaxPerRoute());

        RequestConfig.Builder reqConfig = RequestConfig.custom();
        reqConfig.setConnectTimeout(httpConfig.getConnectTimeout());
        reqConfig.setSocketTimeout(httpConfig.getSocketTimeout());
        reqConfig.setConnectionRequestTimeout(httpConfig.getConnectionRequestTimeout());

        httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(reqConfig.build()).build();
        countDownLatch.countDown();
        log.info("HttpUtil initConfig end");
    }

    /**
     * HttpUtil doPostHttp
     *
     * @param apiUrl
     * @param param  json对象
     * @return
     */
    public String doPostHttp(String apiUrl, String param) throws IOException {
        HttpPost httpPost = new HttpPost(apiUrl);
        // 设置请求头 设置 traceId
        httpPost.setHeader(MoonConstants.TRACE_ID, MDC.get(MoonConstants.TRACE_ID));
        // 设置请求参数
        if (StringUtils.isNoneBlank(param)) {
            StringEntity stringEntity = new StringEntity(param, "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        }
        try {
            boolean await = countDownLatch.await(httpConfig.getWaitHttClientInit(), TimeUnit.MILLISECONDS);
            if (Objects.isNull(httpClient)) {
                log.error("HttpUtil doPostHttp httpClient is null");
                return StringUtils.EMPTY;
            }
        } catch (InterruptedException e) {
            log.error("HttpUtil countDownLatch wait error", e);
        }
        // httpClient 初始化完成，发起对应的 http 请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // EntityUtils.toString 内部会关闭 entity.getContent() 的输入流
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
}