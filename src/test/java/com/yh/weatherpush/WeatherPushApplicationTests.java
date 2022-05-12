package com.yh.weatherpush;

import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        holidayService.isOffDay(LocalDate.now());
        redisService.redisHolidayList(LocalDate.now());
    }

}
