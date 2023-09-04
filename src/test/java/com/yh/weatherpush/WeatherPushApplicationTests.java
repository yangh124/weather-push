package com.yh.weatherpush;

import com.yh.weatherpush.service.WeatherService;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private WeatherService weatherService;

    @Test
    void contextLoads() {
        String a = stringEncryptor.encrypt("ZCcrnO3AMIFlukDL");
        System.out.println(a);
        String b = stringEncryptor.encrypt("0US9MbLBZriYHnpa");
        System.out.println(b);
    }

    @Test
    public void test() {
        String string = weatherService.getLocation("萧山区");
        System.out.println(string);
    }
}
