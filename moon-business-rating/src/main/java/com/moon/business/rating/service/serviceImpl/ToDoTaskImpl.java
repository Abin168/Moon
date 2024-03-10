package com.moon.business.rating.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moon.business.rating.common.util.HttpUtil;
import com.moon.business.rating.service.ToDoTaskInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class ToDoTaskImpl implements ToDoTaskInterface {
    @Autowired
    private HttpUtil httpUtil;
//    public static final ThreadPoolTaskExecutor threadPoolExecutor = SpringHelper.getBean("ratingThreadPoolTaskExecutor");

    @PostConstruct
    public void init() throws IOException {
        httpUtil.doPostHttp("", "");
    }
    @Override
    public int doTask(JSONObject jsonObject) {


//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("future"), threadPoolExecutor);
//        future.join();
        return 0;
    }

    public static void main(String[] args) {
        double d = (69.33 + 74.16) / 2;
        System.out.println(d);
//        BigDecimal yw1 = new BigDecimal("69.33");
//        BigDecimal kp1 = new BigDecimal("74.16");
//
//        BigDecimal yw2 = new BigDecimal("53.54");
//        BigDecimal kpi2 = new BigDecimal("76.77");
//        BigDecimal yw1M = yw1.multiply(new BigDecimal("0.5"));
//        BigDecimal kp1M = kp1.multiply(new BigDecimal("0.5"));
//
//        BigDecimal yw2M = yw2.multiply(new BigDecimal("0.5"));
//        BigDecimal kpi2M = kpi2.multiply(new BigDecimal("0.5"));
//
//        BigDecimal add1 = yw1M.add(kp1M);
//        BigDecimal add2 = yw2M.add(kpi2M);
//
//        BigDecimal multiply = add1.add(add2).multiply(new BigDecimal("0.5"));
//        System.out.println(multiply.toPlainString());
    }
}
