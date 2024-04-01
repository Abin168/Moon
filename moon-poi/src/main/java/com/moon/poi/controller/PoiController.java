package com.moon.poi.controller;

import com.moon.core.common.result.R;
import com.moon.poi.controller.req.PoiReq;
import com.moon.poi.service.IPoi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/poi")
public class PoiController {
    @Autowired
    private IPoi poi;
    @PostMapping("/fillingWord")
    public R<?> poiWord(@Validated @RequestBody PoiReq req) {
        return poi.filling(req.getTempPath(), req.getMap());
    }
}
