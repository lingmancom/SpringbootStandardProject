package com.lm.springbootstandardproject.core.common.log;

import lombok.Data;
@Data
public class WebLogMessage {
    private String url;
    private String userAgent;
    private String runLog;
    private String platform;
    private Integer statusCode;
    private String req_query_string;
    private String req_method;
    private String req_header;
    private String req_body;
    private String res_header;
    private String res_body;
}
