package com.yh.weatherpush.schdule;

import com.yh.weatherpush.service.GetWeatherService;
import com.yh.weatherpush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : yh
 * @date : 2021/10/31 15:12
 */
@Component
public class ScheduledTask {

    @Autowired
    private PushService pushService;

    @Scheduled(cron = "0 5 8 * * ?")
    public void scheduledTask() {
        pushService.pushMsg();
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");
    }
}
