package com.yh.weatherpush;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private QywxService qywxService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        // boolean holiday = holidayService.isOffDay(LocalDate.now());
        // if (holiday) {
        // return;
        // }
        String token = qywxService.getToken();
        List<Tag> list = redisService.redisTagList();
        List<Tag> collect = list.stream().filter(a -> 1 == a.getTagId()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getTomWeather(collect);
        qywxService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

}
