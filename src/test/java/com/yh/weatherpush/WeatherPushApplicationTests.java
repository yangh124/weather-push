package com.yh.weatherpush;

import java.util.HashMap;
import java.util.Map;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.yh.weatherpush.service.QywxService;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private QywxService qywxService;

    @Test
    void contextLoads() {
        String token = qywxService.getPushToken();
        Map<Integer, String> weatherMap = new HashMap<>();
        weatherMap.put(8, "test");
        qywxService.pushWeatherMsg(token, weatherMap);
    }

}
