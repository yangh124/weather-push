package com.yh.weatherpush;

import com.yh.weatherpush.dto.qxwx.Tag;
import com.yh.weatherpush.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;

    @Test
    void contextLoads() {
        String token = pushService.getToken();
        List<Tag> tags = pushService.getTags(token);
        List<Tag> collect = tags.stream().filter(a -> a.getTagid() == 1).collect(Collectors.toList());
        pushService.pushMsg(token, collect);
    }


}
