package com.moon.business.rating.controller.req;

import com.moon.business.rating.common.util.HttpUtil;
import com.moon.core.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hello")
public class TestController {

    @Autowired
    private HttpUtil util;

    @GetMapping("/world")
    public R<?> test() throws IOException {
        util.doPostHttp("","");
        return R.success();
    }
}
