package com.moon.consumer.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "moon-business-rating")
public interface RatingClient {
    @PostMapping("/consumer/userLogin")
    void userLogin(@RequestParam(value = "userId") Integer userId, @RequestParam("phone") String phone);

    @PostMapping("/consumer/userRegister")
    void userRegister(@RequestParam(value = "userId") Integer userId, @RequestParam("phone") String phone);
}
