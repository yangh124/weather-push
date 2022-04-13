package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.schdule.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yh
 * @date : 2022/4/13 21:28
 */
@RestController
@RequestMapping("/tasks")
public class ScheduleTaskController {

    @Autowired
    private ScheduledTask scheduledTask;

    @PatchMapping("/{id}")
    public Result<Void> updateTask(@PathVariable("id") Long id) {
        return Result.success();
    }
}
