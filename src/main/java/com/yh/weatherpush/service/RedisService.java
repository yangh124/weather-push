package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.entity.Tag;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/2/20 13:27
 */
public interface RedisService {

    List<Tag> redisTagList();

    Holiday redisHolidayByKey(LocalDate date);
}
