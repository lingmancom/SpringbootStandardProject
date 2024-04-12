package com.lm.springbootstandardproject.controllers;
import com.lm.springbootstandardproject.models.dto.user.UserLoginDto;
import com.lm.springbootstandardproject.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.lm.tools.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.lm.springbootstandardproject.controllers.BaseController;

/**
 * @author xuyunjie
 */
@RestController
@RequestMapping("/api/account")
@Tag(name = "账户接口", description = "账户接口")
public class AccountController extends BaseController {

    @Resource
    private AccountService accountService;

    @PostMapping("/login")
    public R<String> login(@RequestBody @Validated UserLoginDto userLoginDto) {
        String token = accountService.login(userLoginDto);
        return R.ok(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public R<String> logout() {
        accountService.logout();
        return R.ok();
    }

}
