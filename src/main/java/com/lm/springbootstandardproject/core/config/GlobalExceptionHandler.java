package com.lm.springbootstandardproject.core.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.tools.DemonConstants;
import com.lm.tools.DemonErrorCode;
import com.lm.tools.R;
import com.lm.tools.exception.DemonExceptionCode;
import com.lm.tools.exception.DemonExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

/**
 * @author jacky
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Object> validateException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> list = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            list.add(error.getDefaultMessage());
        }
        return R.error("参数有误！" + list.get(0));
    }

    @ExceptionHandler(value = Exception.class)
    public R<Object> exceptionHandle(final HttpServletRequest request, Exception e) {
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

        return R.systemError("系统错误");
    }

    /*
        * 自定义异常，直接弹窗提示
     */
    @ExceptionHandler(DemonExceptionMessage.class)
    public R<Object> exceptionHandler(DemonExceptionMessage ex) {
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }


    @ExceptionHandler(DemonExceptionCode.class)
    public R<Object> exceptionHandler(DemonErrorCode ex) {
        log.error(ex.getMessage());
        return R.errorCode(ex);
    }

    @ExceptionHandler(value = NotLoginException.class)
    public R<Object> exceptionHandler(NotLoginException e) {
        log.error(e.getMessage());
        return R.errorNoAuth("请先登录");
    }

    @ExceptionHandler(value = NotPermissionException.class)
    public R<Object> exceptionHandler(NotPermissionException e) {
        log.error(e.getMessage());
        // TODO 需要修改code给403
        return R.error("没有权限");
    }


}