package com.lm.SpringBootProject.core.utils.wxrobot;

import lombok.Data;

@Data
public abstract class WechatBotMessage {
    protected String msgtype;

    public WechatBotMessage(String msgtype) {
        this.msgtype = msgtype;
    }
}