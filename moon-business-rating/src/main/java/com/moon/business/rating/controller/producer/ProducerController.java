package com.moon.business.rating.controller.producer;

import com.moon.business.rating.service.KafkaMsgSendTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer")
@Slf4j
public class ProducerController {

    @Autowired
    private KafkaMsgSendTest sendTest;

    @GetMapping("/send")
    public void userRegister() throws InterruptedException {
        sendTest.sendTsgTest();
    }


}
