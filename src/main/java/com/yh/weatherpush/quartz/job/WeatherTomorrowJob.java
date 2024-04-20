package com.yh.weatherpush.quartz.job;

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
 * 明日天气
 *
 * @author : yh
 * @date : 2022/4/18 21:27
 */
@AllArgsConstructor
@Slf4j
@Component
public class WeatherTomorrowJob implements Job {

    private final QywxManager qywxManager;
    private final WeatherService weatherService;
    private final HolidayService holidayService;
    private final LocationService locationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("============= WeatherTomorrowJob start =============");
        String taskId = jobExecutionContext.getJobDetail().getKey().getName();
        List<Location> tagList = locationService.getTagListForJob(taskId);
        if (CollUtil.isNotEmpty(tagList)) {
            LocalDate date = LocalDate.now().plusDays(1);
            boolean holiday = holidayService.isOffDay(date);
            if (holiday) {
                log.info("============= free day =============");
                return;
            }
            Map<Integer, String> map = weatherService.getTomWeather(tagList);
            qywxManager.pushWeatherMsg(map);
            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("{} -> 天气推送成功", format);
        }
        log.info("============= WeatherTomorrowJob end =============");
    }
}
