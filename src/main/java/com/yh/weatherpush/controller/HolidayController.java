package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.entity.Holiday;
import com.yh.weatherpush.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 节假日 前端控制器
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@Api(tags = "节假日")
@RestController
@RequestMapping("/holidays")
public class HolidayController {
    @Autowired
    private RedisService redisService;

    @ApiOperation("获取今年节假日")
    @GetMapping()
    private Result<List<Holiday>> getAllHolidays() {
        List<Holiday> list = redisService.redisHolidayList(LocalDate.now());
        return Result.success(list);
    }
}
