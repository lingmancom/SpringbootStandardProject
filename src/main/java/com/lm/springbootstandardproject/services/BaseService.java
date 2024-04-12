package com.lm.springbootstandardproject.services;

import cn.dev33.satoken.stp.StpUtil;
import com.lm.tools.DemonConstants;
import com.lm.tools.DemonTools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 基础服务类
 */
public  abstract class BaseService {

    @Resource
    protected HttpServletRequest request;


    /**
     * 获取请求中的token
     *
     */

    protected String getToken() {
        return StpUtil.getTokenValue();
    }


    /**
     * 获取请求中的userId
     */
    protected String getUserId() {
        return StpUtil.getLoginIdAsString();
    }

    /**
     * 获取请求中的userAgent
     */
    protected String getUserAgent() {
        return request.getHeader("User-Agent");
    }


    /**
     * 获取请求中的ip
     */
    protected String getIp() {
        return DemonTools.getIpAddr(request);
    }

}
