package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.TagService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.redisson.api.RMap;
import org.redisson.api.RScript;
import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * @author : yh
 * @date : 2022/2/20 13:27
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TagService tagService;
    @Autowired
    private HolidayService holidayService;
    @Value("classpath:lua/hmset.lua")
    private Resource hmsetLua;
    @Value("classpath:lua/sadd.lua")
    private Resource saddLua;

    @Override
    public Holiday redisHolidayByKey(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(formatter);
        int year = date.getYear();
        String key = "holiday:" + year + ":map";
        RMap<String, Holiday> holidayRMap = redissonClient.getMap(key);
        boolean exists = holidayRMap.isExists();
        Map<String, Holiday> map = new HashMap<>();
        if (!exists) {
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
                RScript script = redissonClient.getScript();
                script.eval(Mode.READ_ONLY, RedisScript.of(hmsetLua).getScriptAsString(), ReturnType.VALUE, Collections.singletonList(key), args);
            }
            return map.get(dateStr);
        } else {
            return holidayRMap.get(dateStr);
        }
    }

    @Override
    public List<Holiday> redisHolidayList(LocalDate date) {
        int year = date.getYear();
        String key = "holiday:" + year + ":set";
        RSet<Holiday> holidayRSet = redissonClient.getSet(key);
        boolean exists = holidayRSet.isExists();
        if (!exists) {
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
            RScript script = redissonClient.getScript();
            script.eval(Mode.READ_ONLY, RedisScript.of(saddLua).getScriptAsString(), ReturnType.VALUE, Collections.singletonList(key), args);
            return list;
        } else {
            return new ArrayList<>(holidayRSet);
        }
    }

}
