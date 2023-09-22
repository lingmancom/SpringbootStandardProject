package com.lm.springbootstandardproject.core.common;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @Schema(description = "项目名称")
    @Value("${app.project}")
    private String project;

    @Schema(description = "项目环境")
    @Value("${app.environment}")
    private String environment;

    @Schema(description = "项目端口")
    @Value("${server.port}")
    private String port;

    @Schema(description = "是否使用接口日志")
    @Value("${app.useApiLog}")
    private Boolean useApiLog;

    @Schema(description = "是否使用OpenApi")
    @Value("${app.userSwagger}")
    private Boolean userSwagger;


    @Schema(description = "测试")
    private String test;
}
