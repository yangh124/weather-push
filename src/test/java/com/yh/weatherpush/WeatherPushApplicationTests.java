package com.yh.weatherpush;

import com.yh.weatherpush.component.JwtTokenUtil;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

}
