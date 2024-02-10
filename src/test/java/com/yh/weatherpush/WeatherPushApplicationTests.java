package com.yh.weatherpush;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.yh.weatherpush.controller.AdminController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("dev")
class WeatherPushApplicationTests {

    @Autowired
    private AdminController adminController;

    @Test
    void contextLoads() {
        String s = SaSecureUtil.md5("As071602q");
        System.out.println(s);
    }
}
