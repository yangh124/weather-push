package com.yh.weatherpush;

import com.yh.weatherpush.config.JsonConfig;
import com.yh.weatherpush.dto.Holiday;
import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.PushService;
import com.yh.weatherpush.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private JsonConfig jsonConfig;

    @Test
    void contextLoads() {
        boolean holiday = holidayService.isOffDay(LocalDate.now());
        if (holiday) {
            return;
        }
        String token = pushService.getToken();
        List<TagLocation> list = jsonConfig.getTagLocationList();
        // 嘉定除外
        List<TagLocation> collect = list.stream().filter(a -> 1 == a.getTagid()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getRedisWeather(collect);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

}
