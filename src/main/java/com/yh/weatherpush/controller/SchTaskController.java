package com.yh.weatherpush.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.service.SchTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 定时任务 前端控制器
 * </p>
 *
 * @author yh
 * @since 2022-04-17
 */
@Api(tags = "定时任务管理")
@RestController
@RequestMapping("/tasks")
public class SchTaskController {

    @Autowired
    private SchTaskService schTaskService;

    @ApiOperation("获取定时任务分页")
    @GetMapping("/page")
    public Result<IPage<SchTask>> pageList(PageParam pageParam) {
        IPage<SchTask> res = schTaskService.pageList(pageParam);
        return Result.success(res);
    }

    @ApiOperation("创建定时任务")
    @PostMapping()
    public Result<Void> create(@Validated @RequestBody AddTaskParam param) {
        schTaskService.create(param);
        return Result.success();
    }
}
