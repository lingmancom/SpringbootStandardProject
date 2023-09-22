package com.lm.springbootstandardproject.core.utils.Dbcompare;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//注意prefix要写到最后一个 "." 符号之前
@ConfigurationProperties(prefix="db")
public class DbProperties {
    private String sourceHost;
    private String sourceUser;
    private String sourcePass;
    private String sourceSchema;
    private String sourceCharset;

    private String targetHost;
    private String targetUser;
    private String targetPass;
    private String targetSchema;
    private String targetCharset;

    private String autoExecute;
}
