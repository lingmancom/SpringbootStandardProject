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



    @Schema(description = "项目端口")
    @Value("${server.port}")
    private String port;



    @Schema(description = "测试")
    private String test;
}
