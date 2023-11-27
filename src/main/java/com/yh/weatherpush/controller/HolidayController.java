package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.service.HolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 节假日 前端控制器
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@AllArgsConstructor
@Api(tags = "节假日")
@RestController
@RequestMapping("/holidays")
public class HolidayController {
    private final HolidayService holidayService;

    @ApiOperation("获取今年节假日")
    @GetMapping()
    private Result<List<Holiday>> getAllHolidays() {
        List<Holiday> list = holidayService.redisHolidayList(LocalDate.now());
        return Result.success(list);
    }

    /**
     * 查询今日是否为节假日
     *
     * @return 1-休息 0-工作
     */
    @ApiOperation("查询今日是否为节假日")
    @GetMapping("/isFree")
    private Result<Integer> isFree() {
        // 1-休息 0-工作
        int isFree = 0;
        LocalDate now = LocalDate.now();
        List<Holiday> holidays = holidayService.redisHolidayList(now);
        Map<LocalDate, Boolean> map = holidays.stream().collect(Collectors.toMap(Holiday::getHolidayDate,
                Holiday::getIsOffDay));
        Boolean res = map.get(now);
        if (null == res) {
            DayOfWeek dayOfWeek = now.getDayOfWeek();
            if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek) {
                isFree = 1;
            }
        } else {
            if (res) {
                isFree = 1;
            }
        }
        return Result.success(isFree);
    }
}
