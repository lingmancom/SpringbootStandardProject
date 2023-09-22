package com.lm.springbootstandardproject.core.common.log.log;

import lombok.Data;

/**
 * 记录所有日志信息模型
 */
@Data
public class AliLogMsgModel {

    // 整体数据
    public String url;
    public String userAgent;
    public String runLog;
    public String platform;
    public int statusCode;

    // 请求数据
    public String req_query_string;
    public String req_method;
    public String req_header;
    public String req_body;

    // 返回数据
    public String res_header;
    public String res_body;
}
