package com.moon.consumer;

import lombok.Getter;

@Getter
public enum MsgType {
    User_Log_In(1024),
    User_Register(1025);
    private final int cmd;

    MsgType(int cmd) {
        this.cmd = cmd;
    }
}
