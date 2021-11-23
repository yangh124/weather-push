package com.yh.weatherpush.service;

import java.time.LocalDate;

/**
 * @author : yh
 * @date : 2021/11/21 14:31
 */
public interface HolidayService {

    /**
     * 是否为节假日
     *
     * @param date 日期
     * @return 是、不是
     */
    boolean isHoliday(LocalDate date);

    /**
     * 是否休息
     * @return
     */
    boolean isOffDay(LocalDate date);
}
