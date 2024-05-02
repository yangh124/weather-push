package com.yh.weatherpush.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.infrastructure.po.Holiday;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/21 14:31
 */
public interface HolidayService extends IService<Holiday> {

    /**
     * 是否休息
     *
     * @return 是否休息
     */
    boolean isOffDay(LocalDate date);

    /**
     * 获取某天的节假日信息
     *
     * @param date 日期
     * @return 节假日信息
     */
    Holiday redisHolidayByKey(LocalDate date);

    /**
     * 获取今年的节假日信息
     *
     * @param date 日期
     * @return 节假日信息
     */
    List<Holiday> redisHolidayList(LocalDate date);
}
