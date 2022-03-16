package com.yh.weatherpush;

import cn.hutool.core.collection.CollUtil;
import com.yh.weatherpush.component.JwtTokenUtil;
import com.yh.weatherpush.dto.AdminUserDetails;
import com.yh.weatherpush.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void contextLoads() {
        Admin admin = new Admin();
        admin.setUsername("yh");
        AdminUserDetails adminUserDetails = new AdminUserDetails(admin, CollUtil.newArrayList());
        String s = jwtTokenUtil.generateToken(adminUserDetails);
        System.out.println(s);
        boolean b = jwtTokenUtil.validateToken(s, adminUserDetails);
        System.out.println(b);
    }

}
