package com.lm.springbootstandardproject.core.common.log.log;

import java.lang.annotation.*;


/**
 * 不需要日志
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemonNoLog {

}
