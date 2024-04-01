package com.moon.poi.controller.req;

import com.moon.poi.controller.validate.MapCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class PoiReq {
    @NotBlank(message = "模板路径不能为空")
    private String tempPath;
    @MapCheck
    private Map<String, Object> map;
}
