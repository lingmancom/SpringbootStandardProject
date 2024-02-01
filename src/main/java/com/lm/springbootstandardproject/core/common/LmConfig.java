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
}



