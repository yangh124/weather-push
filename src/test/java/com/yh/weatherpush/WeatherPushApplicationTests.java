package com.yh.weatherpush;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.RedisService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WeatherPushApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        List<Tag> list = redisService.redisTagList();
    }

}
