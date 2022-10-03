package com.yh.weatherpush;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.yh.weatherpush.service.HolidayService;


@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private HolidayService holidayService;

    @Test
    void contextLoads() {
        boolean offDay = holidayService.isOffDay(LocalDate.now());
        System.out.println(offDay);
    }

}
