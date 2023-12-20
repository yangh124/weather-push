package com.yh.weatherpush;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("dev")
class WeatherPushApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
        String a = stringEncryptor.encrypt("ZCcrnO3AMIFlukDL");
        System.out.println(a);
        String b = stringEncryptor.encrypt("0US9MbLBZriYHnpa");
        System.out.println(b);
    }
}
