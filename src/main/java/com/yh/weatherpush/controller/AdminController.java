package com.yh.weatherpush.controller;

import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.admin.LoginParam;
import com.yh.weatherpush.entity.Admin;
import com.yh.weatherpush.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author : yh
 * @date : 2022/3/8 22:08
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody LoginParam param) {
        String token = adminService.login(param);
        return Result.success(token);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result<JSONObject> info(Principal principal) {
        if (principal == null) {
            return Result.unauthorized(null);
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        JSONObject result = new JSONObject();
        result.put("name", admin.getNickName());
        result.put("avatar", admin.getAvatar());
        return Result.success(result);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<JSONObject> logout() {
        return Result.success();
    }
}
