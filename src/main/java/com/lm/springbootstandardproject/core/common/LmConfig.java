package com.lm.springbootstandardproject.core.common;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "lm")
public class LmConfig {
    @Value("${lm.project}")
    private String project;

    @Value("${lm.user-api-log}")
    private Boolean useApiLog;

    @Value("${lm.api-log-config.log-endpoint}")
    private String logEndpoint;

    @Value("${lm.api-log-config.log-access-key-id}")
    private String logAccessKeyId;

    @Value("${lm.api-log-config.log-access-key-secret}")
    private String logAccessKeySecret;
}



