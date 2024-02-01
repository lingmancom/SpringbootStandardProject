package com.lm.springbootstandardproject.controllers;

import com.lm.tools.DemonConstants;
import com.lm.tools.DemonTools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseController {


    @Resource
    protected HttpServletRequest request;


    /**
     * 获取请求中的token
     *
     */

    protected String getToken() {
        return request.getHeader("token") == null ? "" : request.getHeader("token");
    }


    /**
     * 获取请求中的userId
     */
    protected String getUserId() {
        return request.getAttribute(DemonConstants.Log.SenderId) == null ? "" : request.getAttribute(DemonConstants.Log.SenderId).toString();
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
