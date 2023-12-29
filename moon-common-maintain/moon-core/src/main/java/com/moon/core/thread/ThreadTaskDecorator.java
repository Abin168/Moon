package com.moon.core.thread;

import com.moon.core.common.constants.CoreConstants;
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
        String traceId = MDC.get(CoreConstants.TRACE_ID);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return () -> {
            MDC.put(CoreConstants.TRACE_ID, traceId);
            RequestContextHolder.setRequestAttributes(requestAttributes, true);
            runnable.run();
        };
    }
}
