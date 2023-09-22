package com.lm.springbootstandardproject.core.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.tools.DemonConstants;
import com.lm.tools.DemonErrorCode;
import com.lm.tools.R;
import com.lm.tools.exception.DemonCodeException;
import com.lm.tools.exception.DemonMessageException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validateException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> list = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            list.add(error.getDefaultMessage());
        }
        return R.error("参数有误！" + list.get(0));
    }

    @ExceptionHandler(value = Exception.class)
    public R exceptionHandle(final HttpServletRequest request, Exception e) {
        log.error("捕获异常：", e);

        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("stackTrace", Arrays.toString(e.getStackTrace()));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(map);
            request.setAttribute(DemonConstants.Log.exceptionInfo, jsonStr);
        } catch (JsonProcessingException ee) {
            ee.printStackTrace();
        }


        // 是否是正式环境
        if (Objects.equals("prod", System.getProperty("spring.profiles.active"))) {
            return R.systemError("系统错误");
        }else {
            return R.error(e.getMessage());
        }
    }

    @ExceptionHandler(DemonMessageException.class)
    public R<Object> exceptionHandler(DemonMessageException ex) {
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(DemonCodeException.class)
    public R<Object> exceptionHandler(DemonErrorCode ex) {
        log.error(ex.getMessage());
        return R.errorCode(ex);
    }
}