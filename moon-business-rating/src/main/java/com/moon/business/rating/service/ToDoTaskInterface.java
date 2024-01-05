package com.moon.business.rating.service;

import com.alibaba.fastjson.JSONObject;

public interface ToDoTaskInterface {
    default int doTask(JSONObject jsonObject) {
        return 0;
    }
}
