package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.manager.http.HolidayApi;
import com.yh.weatherpush.mapper.HolidayMapper;
import com.yh.weatherpush.service.HolidayService;
import org.redisson.api.RMap;
import org.redisson.api.RScript;
import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2021/11/21 14:34
 */

@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("classpath:lua/hmset.lua")
    private Resource hmsetLua;
    @Value("classpath:lua/sadd.lua")
    private Resource saddLua;
    private final HolidayApi holidayApi;
    private final RedissonClient redissonClient;

    public HolidayServiceImpl(HolidayApi holidayApi, RedissonClient redissonClient) {
        this.holidayApi = holidayApi;
        this.redissonClient = redissonClient;
    }


    @Override
    public boolean isOffDay(LocalDate date) {
        Holiday holiday = redisHolidayByKey(date);
        if (ObjectUtil.isNull(holiday)) {
            int value = date.getDayOfWeek().getValue();
            // 周末放假
            return 6 == value || 7 == value;
        } else {
            // 节假日放假
            return holiday.getIsOffDay();
        }
    }

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
            List<Holiday> list = this.list(queryWrapper);
            if (CollUtil.isEmpty(list)) {
                list = this.getHolidayFromGitHub(year);
            }
            if (CollUtil.isNotEmpty(list)) {
                map = list.stream().collect(Collectors.toMap(a -> a.getHolidayDate().format(formatter), a -> a));
                List<Object> argList = new ArrayList<>();
                argList.add(map.size() << 1 + 2);
                argList.add(86400);
                for (String s : map.keySet()) {
                    argList.add(s);
                    argList.add(map.get(s));
                }
                Object[] args = argList.toArray();
                RScript script = redissonClient.getScript();
                script.eval(Mode.READ_WRITE, RedisScript.of(hmsetLua).getScriptAsString(), ReturnType.VALUE,
                        Collections.singletonList(key), args);
            }
            return map.get(dateStr);
        } else {
            return holidayRMap.get(dateStr);
        }
    }

    public List<Holiday> redisHolidayList(LocalDate date) {
        int year = date.getYear();
        String key = "holiday:" + year + ":set";
        RSet<Holiday> holidayRSet = redissonClient.getSet(key);
        boolean exists = holidayRSet.isExists();
        if (!exists) {
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = this.list(queryWrapper);
            if (CollUtil.isEmpty(list)) {
                list = this.getHolidayFromGitHub(year);
            }
            List<Object> argList = new ArrayList<>();
            argList.add(list.size() + 2);
            argList.add(86400);
            argList.addAll(list);
            Object[] args = argList.toArray();
            RScript script = redissonClient.getScript();
            script.eval(Mode.READ_WRITE, RedisScript.of(saddLua).getScriptAsString(), ReturnType.VALUE,
                    Collections.singletonList(key), args);
            return list;
        } else {
            return new ArrayList<>(holidayRSet);
        }
    }

    private List<Holiday> getHolidayFromGitHub(Integer year) {
        String body = holidayApi.getHolidayFromGitHub(year);
        if (null != body) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(body);
            JSONArray days = jsonObject.getJSONArray("days");
            if (days != null) {
                List<Holiday> list = new ArrayList<>();
                Iterator<Object> iterator = days.stream().iterator();
                while (iterator.hasNext()) {
                    JSONObject next = (JSONObject) iterator.next();
                    String name = next.getString("name");
                    String dateStr = next.getString("date");
                    Boolean isOffDay = next.getBoolean("isOffDay");
                    Holiday holiday = Holiday.builder().holidayName(name).ctime(LocalDateTime.now())
                            .holidayDate(LocalDate.parse(dateStr, format)).isOffDay(isOffDay).year(String.valueOf(year)).build();
                    list.add(holiday);
                }
                saveBatch(list);
                return list;
            }
        }
        return null;
    }
}
