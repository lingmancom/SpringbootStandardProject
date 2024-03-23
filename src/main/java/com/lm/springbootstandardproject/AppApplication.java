package com.lm.springbootstandardproject;

import com.lm.springbootstandardproject.core.common.AppConfig;
import com.lm.springbootstandardproject.core.common.LmConfig;
import com.lm.tools.DemonProjectConfig;
import com.lm.tools.ENV;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition(servers = { @Server(url = "/", description = "Default Server URL") })
@MapperScan(basePackages = { "com.lm.springbootstandardproject.models.entity.mapper", "com.lm.springbootstandardproject.mappers"})
@EnableAsync
@ComponentScan(basePackages = {"com.lm"})
public class AppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AppApplication.class, args);
        LmConfig lmConfig = applicationContext.getBean(LmConfig.class);
        AppConfig appConfig = applicationContext.getBean(AppConfig.class);
        DemonProjectConfig.project = lmConfig.getProject();
        DemonProjectConfig.useFrontApiLog = lmConfig.getUseFrontApiLog();
        DemonProjectConfig.useBackApiLog = lmConfig.getUseBackApiLog();
        Environment environment = new AnnotationConfigApplicationContext().getEnvironment();
        // 获取当前活动的 profiles
        var activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (!activeProfiles.isEmpty()) {
            DemonProjectConfig.environment = ENV.valueOf(activeProfiles.get(0));
        } else {
            DemonProjectConfig.environment = ENV.valueOf("prod");
        }

        DemonProjectConfig.initLogConsumer();
        System.out.println("项目地址：http://localhost:" + appConfig.getPort() + "/doc.html");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
