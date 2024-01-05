package com.moon.business.rating.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moon.business.rating.service.ToDoTaskInterface;
import com.moon.web.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ToDoTaskImpl implements ToDoTaskInterface {
    public static final ThreadPoolTaskExecutor threadPoolExecutor = SpringHelper.getBean("ratingThreadPoolTaskExecutor");

    @Override
    public int doTask(JSONObject jsonObject) {
        log.info("ddddddddddddddddddddddddd");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("future"), threadPoolExecutor);
        future.join();
        return 0;
    }
}
