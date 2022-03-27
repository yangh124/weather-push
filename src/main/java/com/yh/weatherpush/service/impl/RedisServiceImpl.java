package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2022/2/20 13:27
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TagService tagService;
    @Autowired
    private HolidayService holidayService;

    @Override
    public List<Tag> redisTagList() {
        Boolean b = redisTemplate.hasKey("tag:map");
        Map<String, Tag> map = new HashMap<>();
        if (!BooleanUtil.isTrue(b)) {
            List<Tag> list = tagService.list();
            if (CollUtil.isNotEmpty(list)) {
                map = list.stream().collect(Collectors.toMap(a -> String.valueOf(a.getTagId()), a -> a));
                redisTemplate.opsForHash().putAll("tag:map", map);
            }
        } else {
            map = redisTemplate.opsForHash().entries("tag:map");
        }
        Collection<Tag> values = map.values();
        return new ArrayList<>(values);
    }

    @Override
    public Holiday redisHolidayByKey(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(formatter);
        String key = "holiday:" + date.getYear() + ":map";
        Boolean b = redisTemplate.hasKey(key);
        Map<String, Holiday> map = new HashMap<>();
        if (!BooleanUtil.isTrue(b)) {
            int year = LocalDate.now().getYear();
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = holidayService.list(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                map = list.stream().collect(Collectors.toMap(a -> a.getHolidayDate().format(formatter), a -> a));
                redisTemplate.opsForHash().putAll(key, map);
                redisTemplate.expire(key, 24, TimeUnit.HOURS);
            }
            return map.get(dateStr);
        } else {
            Object o = redisTemplate.opsForHash().get(key, dateStr);
            if (null != o) {
                return (Holiday)o;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Holiday> redisHolidayList(LocalDate date) {
        String key = "holiday:" + date.getYear() + ":set";
        Boolean b = redisTemplate.hasKey(key);
        if (!BooleanUtil.isTrue(b)) {
            int year = LocalDate.now().getYear();
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = holidayService.list(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                for (Holiday holiday : list) {
                    redisTemplate.opsForSet().add(key, holiday);
                }
                redisTemplate.expire(key, 24, TimeUnit.HOURS);
            }
            return list;
        } else {
            Set members = redisTemplate.opsForSet().members(key);
            if (null != members) {
                return new ArrayList<Holiday>(members);
            } else {
                return null;
            }
        }
    }

}
