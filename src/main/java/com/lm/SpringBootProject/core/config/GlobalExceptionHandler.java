package com.lm.SpringBootProject.core.config;


import com.lm.SpringBootProject.core.common.CustomException;
import com.lm.SpringBootProject.core.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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
    public R exceptionHandle(Exception e) {
        log.error("捕获异常：", e);
        return R.error(e.getMessage());
        // 是否是正式环境
//        if (true) {
//            return R.systemError("系统错误");
//        } else {
//            return R.error(e.getMessage());
//        }
    }

    @ExceptionHandler(CustomException.class)
    public R exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}