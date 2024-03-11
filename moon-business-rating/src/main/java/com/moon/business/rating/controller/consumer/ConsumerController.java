package com.moon.business.rating.controller.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {

    @PostMapping("/userLogin")
    public void userLogin(@RequestParam(value = "userId") Integer userId, @RequestParam("phone") String phone) {
        log.info("ConsumerController userLogin userId={} phone={}", userId, phone);
    }

    @PostMapping("/userRegister")
    public void userRegister(@RequestParam(value = "userId") Integer userId, @RequestParam("phone") String phone) {
        log.info("ConsumerController userRegister userId={} phone={}", userId, phone);
    }

}
