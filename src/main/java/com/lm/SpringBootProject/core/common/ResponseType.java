package com.lm.SpringBootProject.core.common;



public final class ResponseType {
    /**
     * 成功
     */
    public static final int Ok1 = 1;

    /**
     * 系统程序错误
     */
    public static final int GeneralException2 = 2;

    /**
     * 没有区分的业务错误信息提醒
     */
    public static final int CustomDescription3 = 3;


    /**
     * 404错误，请检查请求url及请求post 或 get方式
     */
    public static final int NotFound404 = 404;

    /**
     * 用户未授权
     */
    public static final int UnAuthorized401 = 401;
}