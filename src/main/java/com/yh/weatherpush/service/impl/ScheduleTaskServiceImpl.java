package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2022/3/26 14:37
 */
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    @Autowired
    private QywxService qywxService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Override
    public void scheduledTask1() {
        boolean holiday = holidayService.isOffDay(LocalDate.now());
        if (holiday) {
            return;
        }
        String token = qywxService.getPushToken();
        List<Tag> list = redisService.redisTagList();
        // 嘉定区
        List<Tag> collect = list.stream().filter(a -> 3 == a.getTagId()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getTodayWeather(collect);
        qywxService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    @Override
    public void scheduledTask2() {
        boolean holiday = holidayService.isOffDay(LocalDate.now());
        if (holiday) {
            return;
        }
        String token = qywxService.getPushToken();
        List<Tag> list = redisService.redisTagList();
        // 嘉定除外
        List<Tag> collect = list.stream().filter(a -> 3 != a.getTagId()).collect(Collectors.toList());
        Map<Integer, String> map = weatherService.getRedisWeather(collect);
        qywxService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 明日天气
     */
    @Override
    public void scheduledTask3() {
        LocalDate date = LocalDate.now().plusDays(1);
        boolean holiday = holidayService.isOffDay(date);
        if (holiday) {
            return;
        }
        String token = qywxService.getPushToken();
        List<Tag> list = redisService.redisTagList();
        Map<Integer, String> map = weatherService.getTomWeather(list);
        qywxService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气推送成功");
    }

    /**
     * 天气灾害预警
     */
    @Override
    public void scheduledTask4() {
        List<Tag> list = redisService.redisTagList();
        Map<Integer, String> map = weatherService.getWeatherWarn(list);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        String token = qywxService.getPushToken();
        qywxService.pushWeatherMsg(token, map);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format + " -> 天气灾害预警成功");
    }
}
