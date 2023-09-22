package com.lm.springbootstandardproject.core.common.log;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * Controller层的日志封装类
 * Created by macro on 2018/4/26.
 */
@Data
public class WebLog {
    private String project;
    private String environment;
    private String apiSenderId;
    private String apiSenderName;
    private long apiElapseTime;
    private String apiPath;
    private String isException;
    private String apiException;
    private String query;
    private String ip;
    private String itemId;
    private String logType;
    private WebLogMessage message;
    @Schema(description = "接口摘要")
    private String summary;
}
