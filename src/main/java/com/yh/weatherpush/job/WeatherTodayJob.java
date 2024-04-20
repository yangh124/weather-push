package com.yh.weatherpush.job;

import cn.hutool.core.collection.CollUtil;
import com.yh.weatherpush.entity.Location;
import com.yh.weatherpush.manager.QywxManager;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.LocationService;
import com.yh.weatherpush.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 今日天气
 *
 * @author : yh
 * @date : 2022/4/18 21:26
 */
@Slf4j
@Component
@AllArgsConstructor
public class WeatherTodayJob implements Job {

    private final QywxManager qywxManager;
    private final WeatherService weatherService;
    private final HolidayService holidayService;
    private final LocationService locationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("============= WeatherTodayJob start =============");
        String taskId = jobExecutionContext.getJobDetail().getKey().getName();
        List<Location> tagList = locationService.getTagListForJob(taskId);
        if (CollUtil.isNotEmpty(tagList)) {
            boolean holiday = holidayService.isOffDay(LocalDate.now());
            if (holiday) {
                log.info("============= free day =============");
                return;
            }
            Map<Integer, String> map = weatherService.getRedisWeather(tagList);
            qywxManager.pushWeatherMsg(map);
            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("{} -> 天气推送成功", format);
        }
        log.info("============= WeatherTodayJob end =============");
    }

}
