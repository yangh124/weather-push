package com.yh.weatherpush;

import com.yh.weatherpush.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;

    @Test
    void contextLoads() {
        pushService.pushMsg();
    }



}
