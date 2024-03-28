package com.moon.business.rating.service;

import com.alibaba.fastjson.JSONObject;
import com.moon.kafka.util.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaMsgSendTest {

    @Autowired
    private KafkaTemplate template;

    public void sendTsgTest() throws InterruptedException {
        JSONObject msg = new JSONObject();
        msg.put("userId", 124444444L);
        msg.put("phone", "131111111");
        sendMsg("moon", 1024, msg.toJSONString());
        Thread.sleep(2000);
        sendMsg("sun", 1025, msg.toJSONString());
        Thread.sleep(2000);
        KafkaUtil.sendToKafka("sun", 1025, msg.toJSONString());
    }
    public void sendMsg(String topic, Integer key, String msg) {
        template.send(topic, key, msg).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("KafkaMsgSendTest send fail topic={} {}", topic, throwable.getMessage());
            }

            @Override
            public void onSuccess(Object object) {
                log.info("KafkaMsgSendTest send success topic={} {}", topic, object);
            }
        });
    }


}
