package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2022/2/20 13:27
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Tag> redisTagList() {
        Map<String, Tag> map = redisTemplate.opsForHash().entries("tag");
        Collection<Tag> values = map.values();
        return new ArrayList<>(values);
    }

    @Override
    public Holiday redisHolidayByKey(LocalDate date) {
        String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Object o = redisTemplate.opsForHash().get("holiday", format);
        return o == null ? null : (Holiday)o;
    }
}
