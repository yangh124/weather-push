package com.yh.weatherpush;

import com.yh.weatherpush.service.HolidayService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private HolidayService holidayService;

    @Test
    void contextLoads() {
        LocalDate date = LocalDate.now().plusDays(1);
        boolean offDay = holidayService.isOffDay(date);
        System.out.println(offDay);
    }

}
