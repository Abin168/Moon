package com.moon.cloud.interceptor;

import com.moon.core.common.constants.MoonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

/**
 * feignInterceptor add traceId
 */
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(MoonConstants.TRACE_ID, MDC.get(MoonConstants.TRACE_ID));
    }
}
