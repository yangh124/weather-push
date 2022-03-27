package com.yh.weatherpush.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.mapper.HolidayMapper;
import com.yh.weatherpush.service.RedisService;
import com.yh.weatherpush.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/21 14:34
 */
@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean isOffDay(LocalDate date) {
        Holiday holiday = redisService.redisHolidayByKey(date);
        if (ObjectUtil.isNull(holiday)) {
            int value = date.getDayOfWeek().getValue();
            // 周末放假
            return 6 == value || 7 == value;
        } else {
            // 节假日放假
            return holiday.getIsOffDay();
        }
    }

}
