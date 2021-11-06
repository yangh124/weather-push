package com.yh.weatherpush;

import com.yh.weatherpush.config.JsonConfig;
import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.service.GetWeatherService;
import com.yh.weatherpush.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest
class WeatherPushApplicationTests {
    @Autowired
    private PushService pushService;
    @Autowired
    private GetWeatherService getWeatherService;
    @Autowired
    private JsonConfig jsonConfig;

    @Test
    void contextLoads() {

        List<TagLocation> list = jsonConfig.getList();
       // List<TagLocation> collect = list.stream().filter(a -> "杭州".equals(a.getTagname())).collect(Collectors.toList());
        Map<Integer, String> map = getWeatherService.getWeatherWarn(list);
        if (CollectionUtils.isEmpty(map)) {
            System.out.println("不发送");
            return;
        }
        // String token = pushService.getToken();
        //pushService.pushWeatherMsg(token, map);
        for (Integer tagid : map.keySet()) {
            System.out.println(map.get(tagid));
        }

    }


}
