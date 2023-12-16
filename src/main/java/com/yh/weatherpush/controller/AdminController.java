package com.yh.weatherpush.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.admin.LoginParam;
import com.yh.weatherpush.dto.admin.UpdPwdParam;
import com.yh.weatherpush.entity.Admin;
import com.yh.weatherpush.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yh
 * @date : 2022/3/8 22:08
 */
@Tag(name = "系统用户")
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginParam param) {
        SaTokenInfo tokenInfo = adminService.login(param);
        return Result.success(tokenInfo.getTokenValue());
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Result<JSONObject> info() {
        String username = (String) StpUtil.getLoginId();
        Admin admin = adminService.getAdminByUsername(username);
        JSONObject result = new JSONObject();
        result.put("name", admin.getNickName());
        result.put("avatar", admin.getAvatar());
        return Result.success(result);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<JSONObject> logout() {
        return Result.success();
    }

    @Operation(summary = "修改密码")
    @PostMapping("/updPwd")
    public Result<Void> updatePassword(@Validated @RequestBody UpdPwdParam updPwdParam) {
        String username = (String) StpUtil.getLoginId();
        updPwdParam.setUsername(username);
        adminService.updatePassword(updPwdParam);
        return Result.success();
    }
}
