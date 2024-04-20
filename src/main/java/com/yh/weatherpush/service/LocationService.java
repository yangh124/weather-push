package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.dto.location.AddLocationParam;
import com.yh.weatherpush.dto.location.LocationDTO;
import com.yh.weatherpush.dto.location.LocationPageParam;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.entity.Location;

import java.util.List;

/**
 * <p>
 * 企业微信标签 服务类
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
public interface LocationService extends IService<Location> {

    void create(AddLocationParam param);

    void delete(Integer id);

    IPage<LocationDTO> pageList(LocationPageParam pageParam);

    List<LocationDTO> getAll();

    void addTagMembers(TagMembersParam param);

    void delTagMembers(TagMembersParam param);

    List<Location> getTagListForJob(String taskId);
}
