package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2022/2/20 13:27
 */
public interface RedisService {

    List<Tag> redisTagList();

    Holiday redisHolidayByKey(LocalDate date);

    List<Holiday> redisHolidayList(LocalDate date);

}
