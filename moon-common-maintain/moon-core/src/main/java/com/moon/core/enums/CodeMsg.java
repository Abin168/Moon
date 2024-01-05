package com.moon.core.enums;

import lombok.Getter;

@Getter
public enum CodeMsg {

    SUCCESS(200, "200", "请求成功"),
    ERROR(500, "500", "服务器开小差了，工程师大大正在努力抢修中");

    private final int code;
    private final String msg;

    CodeMsg(int code, String codeStr, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
