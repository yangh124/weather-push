package com.yh.weatherpush.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.manager.RestApiManager;
import com.yh.weatherpush.mapper.HolidayMapper;
import com.yh.weatherpush.service.HolidayService;
import com.yh.weatherpush.service.RedisService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : yh
 * @date : 2021/11/21 14:34
 */
@Service
public class HolidayServiceImpl extends ServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private RedisService redisService;
    @Autowired
    private RestApiManager restApiManager;


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

    @Override
    public List<Holiday> getHolidayFromGitHub(Integer year) {
        String body = restApiManager.getHolidayFromGitHub(year);
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
