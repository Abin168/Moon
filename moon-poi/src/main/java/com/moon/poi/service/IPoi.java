package com.moon.poi.service;

import com.moon.core.common.result.R;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public interface IPoi {
    default R<?> filling(String tempPath, final Map<String, Object> map) {
        return R.success();
    }
}
