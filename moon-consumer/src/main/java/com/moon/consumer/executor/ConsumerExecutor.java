package com.moon.consumer.executor;

public interface ConsumerExecutor {
    /**
     *  Get ConsumerExecutor
     * @return ConsumerExecutor
     */
    ConsumerExecutor getExecutor();

    /**
     * Kafka Key
     * @return cmd
     */
    int getCmd();

    /**
     * Deal kafka msg Invoke Method
     * @param cmd kafka key
     * @param record kafka record
     * @return result
     */
    default boolean invoke(Integer cmd, String record) {
        return true;
    }
}
