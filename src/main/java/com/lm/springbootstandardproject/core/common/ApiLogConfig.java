package com.lm.springbootstandardproject.core.common;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "api-log-config")
public class ApiLogConfig {
    private String project;
    private String log_endpoint;
    private String log_accessKeyId;
    private String log_accessKeySecret;
    private String environment;
}



