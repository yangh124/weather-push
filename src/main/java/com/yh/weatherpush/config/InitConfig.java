package com.yh.weatherpush.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.TagService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yh
 * @date : 2021/11/2 15:46
 */
@Component
@Getter
@Setter
public class InitConfig {

    @Autowired
    private TagService tagService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        initTag();
        initHoliday();
    }

    private void initTag() {
        Boolean b = redisTemplate.hasKey("tag");
        if (!BooleanUtil.isTrue(b)) {
            List<Tag> list = tagService.list();
            if (CollUtil.isNotEmpty(list)) {
                Map<String, Tag> map =
                    list.stream().collect(Collectors.toMap(a -> String.valueOf(a.getTagId()), a -> a));
                redisTemplate.opsForHash().putAll("tag", map);
            }
        }
    }

    private void initHoliday() {
        Boolean b = redisTemplate.hasKey("holiday");
        if (!BooleanUtil.isTrue(b)) {
            int year = LocalDate.now().getYear();
            LambdaQueryWrapper<Holiday> queryWrapper = new QueryWrapper<Holiday>().lambda().eq(Holiday::getYear, year);
            List<Holiday> list = holidayService.list(queryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Map<String, Holiday> map = list.stream().collect(Collectors
                    .toMap(a -> a.getHolidayDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), a -> a));
                redisTemplate.opsForHash().putAll("holiday", map);
            }
        }
    }

}
