package com.yh.weatherpush;

import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.qxwx.Tag;
import com.yh.weatherpush.service.GetWeatherService;
import com.yh.weatherpush.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;


@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;
    @Autowired
    private GetWeatherService getWeatherService;

    @Test
    void contextLoads() {
        String token = pushService.getToken();
        List<Tag> tags = pushService.getTags(token);
        Map<Integer, String> locations = getWeatherService.getLocations(tags);
        String s = JSONObject.toJSONString(locations);
        System.out.println(s);
    }


}
