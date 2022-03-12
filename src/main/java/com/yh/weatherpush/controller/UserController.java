package com.yh.weatherpush.controller;

import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yh
 * @date : 2022/3/8 22:08
 */
@Api(tags = "系统用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<JSONObject> login() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "12345");
        return Result.success(jsonObject);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result<JSONObject> info() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "yh");
        jsonObject.put("avatar", "https://avatars.githubusercontent.com/u/39212389");
        return Result.success(jsonObject);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<JSONObject> logout() {
        return Result.success();
    }
}
