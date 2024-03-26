package com.moon.poi.common;

import lombok.Getter;

@Getter
public enum ErrorMsgEnum {
    PATH_IS_EMPTY("word 模板路径为空"),
    FILLING_MAP_IS_EMPTY("");
    private final String msg;

    ErrorMsgEnum(String msg) {
        this.msg = msg;
    }
}
