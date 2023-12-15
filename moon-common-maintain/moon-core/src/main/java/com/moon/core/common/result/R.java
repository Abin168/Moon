package com.moon.core.common.result;


import lombok.Getter;

import java.io.Serializable;

@Getter
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    private String traceId;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        return restResult(data, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }


    private static <T> R<T> init(int code, String desc, T data) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setMsg(desc);
        result.setData(data);
        return result;
    }

    public static <T> R<T> success() {
        return init(SUCCESS, "请求成功", null);
    }

    public static <T> R<?> success(String msg, T data) {
        return init(SUCCESS, msg, data);
    }

    public static <T> R<?> success(T data) {
        return init(SUCCESS, "请求成功", data);
    }

    public static <T> R<?> success(int code, String desc, T data) {
        return init(code, desc, data);
    }

    public static <T> R<?> error() {
        return init(FAIL, "系统处理异常", null);
    }

    public static <T> R<?> error(int code) {
        return init(code, "处理异常", null);
    }

    public static <T> R<?> error(int code, String desc) {
        return init(code, desc, null);
    }

    public static <T> R<?> error(int code, String desc, T data) {
        return init(code, desc, data);
    }

    public static <T> R<?> error(String desc) {
        return init(FAIL, desc, null);
    }

    public static <T> R<?> error(String desc, T data) {
        return init(FAIL, desc, data);
    }

    public static <T> R<?> error(R result) {
        return init(result.code, result.msg, result.data);
    }
}
