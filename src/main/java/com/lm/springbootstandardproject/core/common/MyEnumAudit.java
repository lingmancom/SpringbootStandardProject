package com.lm.springbootstandardproject.core.common;

public enum MyEnumAudit {

    UNAUDITED(1, "待审核"),
    PASS(1, "审核通过"),

    NOT_PASS(1, "审核不通过"),
    LINK(1, "LINK"),
    POST(1, "POST"),
    OTHER(1, "OTHER"),
    NOT_PUB(1, "未发布, 草稿箱"),

    //角色类枚举
    SUPERADMIN(1, "超级管理员"),
    ADMIN(2, "管理员"),
    COMMONUSER(4, "普通用户"),
    WITHDRAW(1, "WITHDRAW");
    private String msg;
    private int code;

    MyEnumAudit(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
