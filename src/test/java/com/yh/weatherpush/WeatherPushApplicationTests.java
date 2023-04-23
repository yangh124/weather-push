package com.yh.weatherpush;

import com.yh.weatherpush.manager.api.HolidayApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private HolidayApiClient holidayApiClient;

    @Test
    void contextLoads() {
        int year = LocalDate.now().getYear();
        String str = holidayApiClient.getHolidayFromGitHub(year);
        System.out.println(str);
    }

}
