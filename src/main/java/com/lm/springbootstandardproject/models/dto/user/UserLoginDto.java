package com.lm.springbootstandardproject.models.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author xuyunjie
 * @date 2024/4/12 10:41
 **/
@Data
public class UserLoginDto {
    @Schema(description = "用户账号")
    @NotNull(message = "用户账号不能为空")
    private String account;

    @Schema(description = "用户密码")
    @NotNull(message = "用户密码不能为空")
    private String password;
}
