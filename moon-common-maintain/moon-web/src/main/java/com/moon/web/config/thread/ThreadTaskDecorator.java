package com.moon.web.config.thread;

import com.moon.core.common.constants.MoonConstants;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * thread task decorator
 */

@SuppressWarnings("all")
public class ThreadTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        String traceId = MDC.get(MoonConstants.TRACE_ID);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return () -> {
            MDC.put(MoonConstants.TRACE_ID, traceId);
            RequestContextHolder.setRequestAttributes(requestAttributes, true);
            runnable.run();
        };
    }
}
