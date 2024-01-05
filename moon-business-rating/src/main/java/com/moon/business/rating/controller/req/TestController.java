package com.moon.business.rating.controller.req;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/hello")
public class TestController {

    @GetMapping("world")
    public void test() {
        int i = 1/ 0;
        log.info("hello world");
    }
}


