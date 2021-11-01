package com.yh.weatherpush.schdule;

import com.yh.weatherpush.dto.qxwx.Tag;
import com.yh.weatherpush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2021/10/31 15:12
 */
@Component
public class ScheduledTask {

    @Autowired
    private PushService pushService;

    @Scheduled(cron = "0 10 7 * * ?")
    public void scheduledTask1() {
        String token = pushService.getToken();
        List<Tag> tags = pushService.getTags(token);
        List<Tag> collect = tags.stream().filter(a -> "嘉定".equals(a.getTagname())).collect(Collectors.toList());
        pushService.pushMsg(token, collect);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");
    }


    @Scheduled(cron = "0 5 8 * * ?")
    public void scheduledTask2() {
        String token = pushService.getToken();
        List<Tag> tags = pushService.getTags(token);
        List<Tag> collect = tags.stream().filter(a -> !"嘉定".equals(a.getTagname())).collect(Collectors.toList());
        pushService.pushMsg(token, collect);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");
    }
}
