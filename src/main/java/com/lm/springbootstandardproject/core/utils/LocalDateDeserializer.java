package com.lm.springbootstandardproject.core.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// 自定义反序列化器
  class LocalDateDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONLexer lexer = parser.lexer;
        String dateString = lexer.stringVal();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return (T) localDate;
        } catch (Exception e) {
            throw new JSONException("Error parsing LocalDate object.", e);
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
