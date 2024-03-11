package com.moon.consumer.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ExecutorMapping
 */
@Slf4j
public class ExecutorMapping {
    private static final ExecutorMapping mapping = new ExecutorMapping();
    private static final ConcurrentHashMap<Integer, ConsumerExecutor> map = new ConcurrentHashMap<>();

    public static ExecutorMapping getInstance() {
        return mapping;
    }

    public void register(ConsumerExecutor executor) {
        int cmd = executor.getCmd();
        ConsumerExecutor consumerExecutor = executor.getExecutor();
        log.info("ExecutorMapping register cmd={} executor={}", cmd, consumerExecutor);
        map.put(cmd, consumerExecutor);
    }

    public boolean invoke(Integer cmd, String msg) {
        boolean result = true;
        if (map.containsKey(cmd)) {
            ConsumerExecutor executor = map.get(cmd);
            try {
                return executor.invoke(cmd, msg);
            } catch (Exception e) {
                log.error("ExecutorMapping invoke cmd={} executor={} msg={}", cmd, executor.toString(), msg);
                return false;
            }
        }
        return result;
    }


}
