package com.yh.weatherpush.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.dto.tag.TagPageParam;
import com.yh.weatherpush.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Tag(name = "标签管理")
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "创建标签")
    @PostMapping()
    public Result<Void> create(@Validated @RequestBody AddTagParam param) {
        tagService.create(param);
        return Result.success();
    }

    @Operation(summary = "删除标签")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        return Result.success();
    }

    @Operation(summary = "获取标签分页")
    @GetMapping("/page")
    public Result<IPage<TagDTO>> pageList(TagPageParam pageParam) {
        IPage<TagDTO> res = tagService.pageList(pageParam);
        return Result.success(res);
    }

    @Operation(summary = "获取所有标签")
    @GetMapping()
    public Result<List<TagDTO>> pageList() {
        List<TagDTO> res = tagService.getAll();
        return Result.success(res);
    }

    @Operation(summary = "添加标签成员")
    @PostMapping("/members")
    public Result<Void> addTagMembers(@Validated @RequestBody TagMembersParam param) {
        tagService.addTagMembers(param);
        return Result.success();
    }

    @Operation(summary = "删除标签成员")
    @DeleteMapping("/members")
    public Result<Void> delTagMembers(@Validated @RequestBody TagMembersParam param) {
        tagService.delTagMembers(param);
        return Result.success();
    }

}
