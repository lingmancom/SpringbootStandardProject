package com.lm.SpringBootProject.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author 蔡美庭 1628446946@qq.com
 */
public class JWTUtils {


    private static final String SING = "luzhi123213";


    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 1);
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        //指定过期时间
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));


        return token;
    }

    //校验令牌
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }


}