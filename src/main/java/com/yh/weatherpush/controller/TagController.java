package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 企业微信标签 前端控制器
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("创建标签")
    @PostMapping("/create")
    public Result<Void> create(@Validated @RequestBody AddTagParam param) {
        tagService.create(param);
        return Result.success();
    }

}
