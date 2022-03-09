package com.yh.weatherpush.controller;

import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yh
 * @date : 2022/3/8 22:08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Result<JSONObject> login() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", "12345");
        return Result.success(jsonObject);
    }

    @GetMapping("/info")
    public Result<JSONObject> info() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "yh");
        jsonObject.put("avatar", "");
        return Result.success(jsonObject);
    }

    @PostMapping("/logout")
    public Result<JSONObject> logout() {
        return Result.success();
    }
}
