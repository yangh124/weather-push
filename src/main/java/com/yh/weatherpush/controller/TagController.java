package com.yh.weatherpush.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("创建标签")
    @PostMapping()
    public Result<Void> create(@Validated @RequestBody AddTagParam param) {
        tagService.create(param);
        return Result.success();
    }

    @ApiOperation("删除标签")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        return Result.success();
    }

    @ApiOperation("获取标签分页")
    @GetMapping("/page")
    public Result<IPage<TagDTO>> pageList(PageParam pageParam) {
        IPage<TagDTO> res = tagService.pageList(pageParam);
        return Result.success(res);
    }

    @ApiOperation("获取所有标签")
    @GetMapping()
    public Result<List<TagDTO>> pageList() {
        List<TagDTO> res = tagService.getAll();
        return Result.success(res);
    }
}
