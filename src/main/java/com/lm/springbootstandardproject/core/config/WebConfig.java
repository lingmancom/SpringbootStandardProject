package com.lm.springbootstandardproject.core.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.springbootstandardproject.core.common.JacksonObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 默认放行
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/v3/api-docs/**", "/__inspector__", "/doc.html", "/api/system/version", "/api/account/login")
                .excludePathPatterns("/**/css/**", "/**/js/**", "/favicon.ico")
                .excludePathPatterns("/api/base/**");
    }

    /**
     * 扩展mvc框架消息转换器
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建自定义的消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置自定义的JSON解析器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将消息转换器添加到集合中
        converters.add(1, messageConverter);
    }
}
