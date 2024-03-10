package com.moon.business.rating.service;

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
        int i = 0;
        while (true) {
            Thread.sleep(2000);
            sendMsg("sendMsg" + i);
            Thread.sleep(2000);
            sendMsg2("sendMsg2" + i);
            Thread.sleep(2000);
            i ++;
        }
    }

    public void sendMsg(String msg) {
        KafkaUtil.sendToKafka("moon", 1024, msg);
    }

    public void sendMsg2(String msg) {
        template.send("moon", 1024, msg).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("KafkaMsgSendTest send fail {}", throwable.getMessage());
            }

            @Override
            public void onSuccess(Object object) {
                log.info("KafkaMsgSendTest send success {}", object);
            }
        });
    }


}
