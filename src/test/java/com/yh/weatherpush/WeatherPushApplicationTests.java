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
import java.util.List;
import java.util.Map;


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
//        boolean holiday = holidayService.isHoliday(null);
//        if (holiday) {
//            return;
//        }
//        List<TagLocation> list = jsonConfig.getTagLocationList();
//        Map<Integer, String> map = weatherService.getWeatherWarn(list);
//        if (CollectionUtils.isEmpty(map)) {
//            return;
//        }
//        String token = pushService.getToken();
//        pushService.pushWeatherMsg(token, map);
//        LocalDateTime now = LocalDateTime.now();
//        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        System.out.println(format + " -> 天气推送成功");
        System.out.println(holidayService.isOffDay(LocalDate.of(2022, 1, 1)));

        System.out.println(holidayService.isOffDay(LocalDate.of(2022, 1, 29)));

        System.out.println(holidayService.isOffDay(LocalDate.now()));
    }


}
