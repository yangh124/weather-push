package com.yh.weatherpush.quartz.job;

import cn.hutool.core.collection.CollUtil;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.TagService;
import com.yh.weatherpush.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Component
public class WeatherTomorrowJob implements Job {

    @Autowired
    private QywxService qywxService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private TagService tagService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("============= WeatherTomorrowJob start =============");
        String taskId = jobExecutionContext.getJobDetail().getKey().getName();
        List<Tag> tagList = tagService.getTagListForJob(taskId);
        if (CollUtil.isNotEmpty(tagList)) {
            LocalDate date = LocalDate.now().plusDays(1);
            boolean holiday = holidayService.isOffDay(date);
            if (holiday) {
                return;
            }
            String token = qywxService.getPushToken();
            Map<Integer, String> map = weatherService.getTomWeather(tagList);
            qywxService.pushWeatherMsg(token, map);
            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info(format + " -> 天气推送成功");
        }
        log.info("============= WeatherTomorrowJob end =============");
    }
}
