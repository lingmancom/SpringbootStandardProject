package com.lm.springbootstandardproject.services;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lm.springbootstandardproject.models.dto.user.UserLoginDto;
import com.lm.tools.exception.DemonExceptionMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AccountService extends BaseService {
    public String login(UserLoginDto userLoginDto) {
        // 1. 获取用户信息
        // 2.直接调用登录
        // StpUtil.login(用户ID);
        // 3.返回token
        return StpUtil.getTokenInfo().getTokenValue();
    }

    public void logout() {
        // 1.直接调用登出
        // StpUtil.logout();
    }
}
