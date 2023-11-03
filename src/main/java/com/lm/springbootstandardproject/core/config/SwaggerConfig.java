package com.lm.springbootstandardproject.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        private static final String headerName = "Token";// 请求头名称

        @Bean
        public OpenAPI openApi() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("接口文档")
                                                .description("接口文档")
                                                .contact(new Contact().name("lingman").email("邮箱").url("公司官网地址"))
                                                .version("v1.0"))
                                .addSecurityItem(new SecurityRequirement().addList(headerName))
                                .components(components());
        }

        private Components components() {
                return new Components()
                                .addSecuritySchemes(
                                                headerName,
                                                new SecurityScheme()
                                                                .type(SecurityScheme.Type.APIKEY)
                                                                .scheme("basic")
                                                                .name(headerName)
                                                                .in(SecurityScheme.In.HEADER)
                                                                .description("token令牌"));
        }
}
