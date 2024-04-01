package com.moon.consumer.executor.user;

import com.alibaba.fastjson.JSONObject;
import com.moon.consumer.common.MsgType;
import com.moon.consumer.executor.ConsumerExecutor;
import com.moon.consumer.executor.ExecutorMapping;
import com.moon.consumer.remote.RatingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UserLoginExecutor implements ConsumerExecutor, InitializingBean {
    @Resource
    private RatingClient ratingClient;

    @Override
    public boolean invoke(Integer cmd, String record) {
        log.info("UserLoginExecutor invoke cmd={} record={}", cmd, record);
        JSONObject msg = JSONObject.parseObject(record);
        Integer userId = msg.getInteger("userId");
        String phone = msg.getString("phone");
        ratingClient.userLogin(userId, phone);
        return true;
    }

    @Override
    public ConsumerExecutor getExecutor() {
        return this;
    }

    @Override
    public int getCmd() {
        return MsgType.User_Register.getCmd();
    }

    @Override
    public void afterPropertiesSet() {
        ExecutorMapping.getInstance().register(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
