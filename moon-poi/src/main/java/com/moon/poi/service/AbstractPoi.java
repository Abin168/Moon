package com.moon.poi.service;

import com.alibaba.nacos.common.utils.MapUtils;
import com.moon.core.common.result.R;
import com.moon.poi.common.ErrorMsgEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public abstract class AbstractPoi implements IPoi {
    public abstract R<?> wordFilling(String tempPath, Map<String, Object> map);

    @Override
    public R<?> Filling(String tempPath, Map<String, Object> map) {
        if (StringUtils.isEmpty(tempPath)) {
            return R.error(ErrorMsgEnum.PATH_IS_EMPTY.getMsg());
        }
        if (MapUtils.isEmpty(map)) {
            return R.error(ErrorMsgEnum.FILLING_MAP_IS_EMPTY.getMsg());
        }
        return wordFilling(tempPath, map);
    }
}
