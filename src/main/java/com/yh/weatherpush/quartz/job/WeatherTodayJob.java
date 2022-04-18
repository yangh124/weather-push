package com.yh.weatherpush.quartz.job;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2022/4/18 21:26
 */
@Slf4j
@Component
public class WeatherTodayJob implements Job {

    @Autowired
    private QywxService qywxService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisService redisService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        log.info("============= WeatherTodayJob start =============");
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
        log.info(format + " -> 天气推送成功");
        log.info("============= WeatherTodayJob end =============");
    }

}
