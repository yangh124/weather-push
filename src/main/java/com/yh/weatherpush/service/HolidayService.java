package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.entity.Holiday;

import java.time.LocalDate;

/**
 * @author : yh
 * @date : 2021/11/21 14:31
 */
public interface HolidayService extends IService<Holiday> {

    /**
     * 是否休息
     * @return
     */
    boolean isOffDay(LocalDate date);
}
