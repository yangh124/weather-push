package com.yh.weatherpush.manager.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 */
@FeignClient(name = "holiday-api", url = "${holiday.url}")
public interface HolidayApiClient {

    /**
     * 获取节假日信息
     *
     * @param year 年份
     * @return 节假日信息
     */
    @GetMapping("/{year}.json")
    String getHolidayFromGitHub(@PathVariable("year") Integer year);
}
