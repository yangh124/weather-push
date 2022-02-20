package com.yh.weatherpush.schdule;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.WeatherService;
import com.yh.weatherpush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2021/10/31 15:12
 */
@Component
public class ScheduledTask {

    @Autowired
    private PushService pushService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0 10 7 * * ?")
    public void scheduledTask1() {
        boolean holiday = holidayService.isOffDay(LocalDate.now());
        if (holiday) {
            return;
        }
        String token = pushService.getToken();
        List<Tag> list = redisService.redisTagList();
        // 嘉定区
        List<Tag> collect = list.stream().filter(a -> 3 == a.getTagId()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getTodayWeather(collect);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 今日天气
     */
    @Scheduled(cron = "0 5 8 * * ?")
    public void scheduledTask2() {
        boolean holiday = holidayService.isOffDay(LocalDate.now());
        if (holiday) {
            return;
        }
        String token = pushService.getToken();
        List<Tag> list = redisService.redisTagList();
        // 嘉定除外
        List<Tag> collect = list.stream().filter(a -> 3 != a.getTagId()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getRedisWeather(collect);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 明日天气
     */
    @Scheduled(cron = "0 30 20 * * ?")
    public void scheduledTask3() {
        LocalDate date = LocalDate.now().plusDays(1);
        boolean holiday = holidayService.isOffDay(date);
        if (holiday) {
            return;
        }
        String token = pushService.getToken();
        List<Tag> list = redisService.redisTagList();
        Map<Integer, String> map = weatherService.getTomWeather(list);
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 天气灾害预警
     */
    // @Scheduled(cron = "0 0 0/1 * * ?")
    public void scheduledTask4() {
        List<Tag> list = redisService.redisTagList();
        Map<Integer, String> map = weatherService.getWeatherWarn(list);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        String token = pushService.getToken();
        pushService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气灾害预警成功");
    }
}
