package com.lm.springbootstandardproject.core.config;

import cn.hutool.json.JSONUtil;
import com.lm.springbootstandardproject.core.config.SqlStatisticsThreadLocalUtil;
import com.lm.tools.DemonProjectConfig;
import com.lm.tools.log.DemonNoLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * SQL 统计信息 AOP
 *
 * @author cq
 * @date 2023/11/04
 */
@Aspect
@Configuration
@Slf4j
public class SqlStatisticsAop {

    @Resource
    private DataSource dataSource;

    @Around("execution(* com.lm.springbootstandardproject.controllers..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        DemonNoLog annotation = getAnnotation(joinPoint, DemonNoLog.class);
        if (annotation != null) {
            return joinPoint.proceed();
        }
        // 如果不是http请求，直接返回
        if (requestAttributes == null) {
            return joinPoint.proceed();
        }

        // 先执行请求，目的是拿到所有sql语句
        Object result = joinPoint.proceed();

        // 设置request
        HttpServletRequest request = requestAttributes.getRequest();
        Map<String, Integer> currentMap = SqlStatisticsThreadLocalUtil.getCurrentMap();
        List<Map<String, Object>> sqlList = new LinkedList<>();
        if (currentMap == null) {
            return result;
        }
        currentMap.forEach((sql, count) -> sqlList.add(new HashMap<>(2) {{
            put("sqlCount", count);
            put("sql", sql);
        }}));
        SqlStatisticsThreadLocalUtil.removeCurrentMap();
        String sqlStatisticsLog = JSONUtil.toJsonStr(sqlList);
        log.info("runLog: {}", sqlList);
        request.setAttribute("runLog", sqlStatisticsLog);
        return result;
    }

    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Class<?> targetClass = joinPoint.getTarget().getClass();
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

            Method method = targetClass.getMethod(methodName, parameterTypes);
            return method.getAnnotation(annotationClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
