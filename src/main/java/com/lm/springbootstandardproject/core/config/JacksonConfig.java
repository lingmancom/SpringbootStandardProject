package com.lm.springbootstandardproject.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .build();
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(new MyBeanSerializerModifier()));
        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new CustomizeNullJsonSerializer
                .NullObjectJsonSerializer());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        return objectMapper;
    }

    public static class CustomDateFormat extends StdDateFormat {
        @Override
        public String toString() {
            return "yyyy-MM-dd HH:mm:ss";
        }
    }
}
