package com.lm.SpringBootProject.core.common.log.log;


import lombok.Data;

/**
 * 记录所有日志模型
 */
@Data
public class AliLogFullModel {


    /**
     * 环境名称：dev, pro
     */
    public String environment;
    /**
     * 用户ID
     */
    public String apiSenderId;

    /**
     * 用户名
     */
    public String apiSenderName;

    /**
     * api调用时间
     */
    public long apiElaspseTime;

    /**
     * api的路径
     */
    public String apiPath;

    /**
     * 是否发生exception
     */
    public String isException;

    /**
     * Exception内容
     */
    public String apiException;

    /**
     * 查询用
     */
    public String query;

    /**
     * ip
     */
    public String ip;

    /**
     * 序列化所有访问的其他信息
     */
    public String message;

    /**
     * 日志描述
     */
    private String summary;
}
