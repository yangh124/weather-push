package com.yh.weatherpush;

import com.yh.weatherpush.config.JsonConfig;
import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;
    @Autowired
    private JsonConfig jsonConfig;

    @Test
    void contextLoads() {
        String token = pushService.getToken();
        List<TagLocation> list = jsonConfig.getList();
        List<TagLocation> collect = list.stream().filter(a -> "杭州".equals(a.getTagname())).collect(Collectors.toList());
        pushService.pushMsg(token, collect);
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(format + " -> 天气推送成功");

    }


}
