package com.lm.SpringBootProject;

import com.lm.SpringBootProject.core.common.ApiLogConfig;
import com.lm.SpringBootProject.core.common.AppConfig;
import com.lm.SpringBootProject.core.common.log.DemonProjectConfig;
import com.lm.SpringBootProject.core.common.log.ENV;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@MapperScan(basePackages = {"com.lm.SpringBootProject.models.entity.mapper"})
@EnableAsync
public class AppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppApplication.class, args);
        var apiLogConfig = applicationContext.getBean(ApiLogConfig.class);
        var appConfig = applicationContext.getBean(AppConfig.class);
        var env = appConfig.getEnvironment();
        DemonProjectConfig.project = apiLogConfig.getProject();
        DemonProjectConfig.log_endpoint = apiLogConfig.getLog_endpoint();
        DemonProjectConfig.log_accessKeyId = apiLogConfig.getLog_accessKeyId();
        DemonProjectConfig.log_accessKeySecret = apiLogConfig.getLog_accessKeySecret();
        DemonProjectConfig.environment = ENV.valueOf(appConfig.getEnvironment());
        System.out.println("项目地址：http://localhost:" + appConfig.getPort() + "/swagger-ui/index.html");
        var ss = appConfig.getTest();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
