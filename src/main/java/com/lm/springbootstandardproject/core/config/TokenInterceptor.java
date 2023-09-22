package com.lm.springbootstandardproject.core.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.springbootstandardproject.core.common.R;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class TokenInterceptor implements HandlerInterceptor {
    //jwt拦截

/*    @Autowired
    private TUserMapper tUserMapper;*/

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

/*    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Token.class)) {
            return true;
        } else {
            String token = request.getHeader("token");
            Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries("login:token:" + token);
            if (userMap.isEmpty()) {
                send401ToResponse(response, "身份认证已过期");
                return false;
            }
            // 3.判断用户是否存在
            // 获取userMap中的用户信息
            TUser userObj = new TUser();
            String id = userMap.get("id").toString();
            if (id == null) {
                send401ToResponse(response, "身份认证已过期");
                return false;
            }
            userObj.setId(id);

            // 7.刷新token有效期
            stringRedisTemplate.expire("login:token:" + token, 48, TimeUnit.HOURS);
            // 8.放行
            if (userObj == null) {
                send401ToResponse(response, "没有权限");
                return false;
            }
            request.setAttribute("userId", userObj.getId());
            return true;
        }
    }*/


    private void send401ToResponse(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json");

        // 创建错误消息对象
        R errorMessage = R.errorNoAuth();

        // 将错误消息对象转换成 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonErrorMessage = objectMapper.writeValueAsString(errorMessage);

        // 发送 JSON 错误消息给客户端
        response.getWriter().write(jsonErrorMessage);
    }
}
