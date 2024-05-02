package com.yh.weatherpush.infrastructure.manager.http;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface HolidayApi {

    /**
     * 获取节假日信息
     *
     * @param year 年份
     * @return 节假日信息
     */
    @GetExchange(url = "/{year}.json")
    String getHolidayFromGitHub(@PathVariable("year") Integer year);
}
