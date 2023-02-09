package com.yh.weatherpush;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    void contextLoads() {
        Tag tag = new Tag();
        tag.setTagId(8);
        List<Tag> tags = new ArrayList<>(1);
        tags.add(tag);
        Map<Integer, String> redisWeather = weatherService.getRedisWeather(tags);
        System.out.println(redisWeather);
    }

}
