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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

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
    @Value("classpath:lua/hmset.lua")
    private Resource hmsetLua;
    @Value("classpath:lua/sadd.lua")
    private Resource saddLua;

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
        int year = date.getYear();
        String key = "holiday:" + year + ":map";
        Boolean b = redisTemplate.hasKey(key);
        Map<String, Holiday> map = new HashMap<>();
        if (!BooleanUtil.isTrue(b)) {
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = holidayService.list(queryWrapper);
            if (CollUtil.isEmpty(list)) {
                list = holidayService.getHolidayFromGitHub(year);
            }
            if (CollUtil.isNotEmpty(list)) {
                map = list.stream().collect(Collectors.toMap(a -> a.getHolidayDate().format(formatter), a -> a));
                List<Object> argList = new ArrayList<>();
                argList.add(map.size() << 1 + 2);
                argList.add(86400L);
                for (String s : map.keySet()) {
                    argList.add(s);
                    argList.add(map.get(s));
                }
                Object[] args = argList.toArray();
                redisTemplate.execute(RedisScript.of(hmsetLua), Collections.singletonList(key), args);
            }
            return map.get(dateStr);
        } else {
            // 添加双引号
            String field = "\"" + dateStr + "\"";
            Object o = redisTemplate.opsForHash().get(key, field);
            if (null != o) {
                return (Holiday) o;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Holiday> redisHolidayList(LocalDate date) {
        int year = date.getYear();
        String key = "holiday:" + year + ":set";
        Boolean b = redisTemplate.hasKey(key);
        if (!BooleanUtil.isTrue(b)) {
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = holidayService.list(queryWrapper);
            if (CollUtil.isEmpty(list)) {
                list = holidayService.getHolidayFromGitHub(year);
            }
            List<Object> argList = new ArrayList<>();
            argList.add(list.size() + 2);
            argList.add(86400L);
            argList.addAll(list);
            Object[] args = argList.toArray();
            redisTemplate.execute(RedisScript.of(saddLua), Collections.singletonList(key), args);
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
