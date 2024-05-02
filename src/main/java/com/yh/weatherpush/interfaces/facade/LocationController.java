package com.yh.weatherpush.interfaces.facade;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.application.service.LocationService;
import com.yh.weatherpush.interfaces.dto.Result;
import com.yh.weatherpush.interfaces.dto.location.AddLocationParam;
import com.yh.weatherpush.interfaces.dto.location.LocationDTO;
import com.yh.weatherpush.interfaces.dto.location.LocationPageParam;
import com.yh.weatherpush.interfaces.dto.tag.TagMembersParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区管理 前端控制器
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@AllArgsConstructor
@Tag(name = "地区管理")
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "创建地区")
    @PostMapping()
    public Result<Void> create(@Validated @RequestBody AddLocationParam param) {
        locationService.create(param);
        return Result.success();
    }

    @Operation(summary = "删除地区")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Integer id) {
        locationService.delete(id);
        return Result.success();
    }

    @Operation(summary = "获取地区分页")
    @GetMapping("/page")
    public Result<IPage<LocationDTO>> pageList(LocationPageParam pageParam) {
        IPage<LocationDTO> res = locationService.pageList(pageParam);
        return Result.success(res);
    }

    @Operation(summary = "获取所有地区")
    @GetMapping()
    public Result<List<LocationDTO>> pageList() {
        List<LocationDTO> res = locationService.getAll();
        return Result.success(res);
    }

    @Operation(summary = "添加地区成员")
    @PostMapping("/members")
    public Result<Void> addTagMembers(@Validated @RequestBody TagMembersParam param) {
        locationService.addTagMembers(param);
        return Result.success();
    }

    @Operation(summary = "删除地区成员")
    @DeleteMapping("/members")
    public Result<Void> delTagMembers(@Validated @RequestBody TagMembersParam param) {
        locationService.delTagMembers(param);
        return Result.success();
    }

}
