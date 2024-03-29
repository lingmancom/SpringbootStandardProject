package com.lm.springbootstandardproject.core.common;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "lm")
public class LmConfig {
    private String project;

    private Boolean useFrontApiLog = false;

    private Boolean useBackApiLog = false;

    private String logEndpoint =  "cn-shanghai.log.aliyuncs.com";
}



