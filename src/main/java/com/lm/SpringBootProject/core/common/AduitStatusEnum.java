package com.lm.SpringBootProject.core.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AduitStatusEnum {
    /*
     * 数据
     * */
    UNAUDITED(1, "UNAUDITED"),
    PASS(1, "PASS"),
    NOT_PASS(1, "NOT_PASS");
    @EnumValue
    @JsonValue
    private String msg;
    private int code;
    AduitStatusEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    @JsonCreator
    public static AduitStatusEnum getItem(String msg) {
        for (AduitStatusEnum item : values()) {
            if (item.getMsg().equals(msg)) {
                return item;
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
