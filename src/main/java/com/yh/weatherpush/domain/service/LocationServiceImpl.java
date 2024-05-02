package com.yh.weatherpush.domain.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.application.event.TagDeleteEvent;
import com.yh.weatherpush.application.service.LocationService;
import com.yh.weatherpush.application.service.WeatherService;
import com.yh.weatherpush.infrastructure.exception.ApiException;
import com.yh.weatherpush.infrastructure.manager.QywxManager;
import com.yh.weatherpush.infrastructure.mapper.LocationMapper;
import com.yh.weatherpush.infrastructure.mapper.TaskRelLocationMapper;
import com.yh.weatherpush.infrastructure.po.Location;
import com.yh.weatherpush.infrastructure.po.TaskRelLocation;
import com.yh.weatherpush.interfaces.dto.location.AddLocationParam;
import com.yh.weatherpush.interfaces.dto.location.LocationDTO;
import com.yh.weatherpush.interfaces.dto.location.LocationPageParam;
import com.yh.weatherpush.interfaces.dto.qywx.QywxTagDTO;
import com.yh.weatherpush.interfaces.dto.tag.TagMembersParam;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 企业微信标签 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@AllArgsConstructor
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements LocationService {

    private final QywxManager qywxManager;
    private final WeatherService weatherService;
    private final TaskRelLocationMapper taskRelLocationMapper;
    private final ApplicationContext applicationContext;


    @Override
    public void create(AddLocationParam param) {
        Integer tagId = qywxManager.createTag(param.getLocationName());
        String code = weatherService.getLocation(param.getLocationName());
        Location location = new Location();
        location.setTagId(tagId);
        location.setCode(code);
        location.setCtime(LocalDateTime.now());
        location.setLocationName(param.getLocationName());
        super.save(location);
    }

    @Override
    public void delete(Integer id) {
        Location tag = super.getById(id);
        if (null == tag) {
            throw new ApiException("地区不存在");
        }
        super.removeById(id);
        // 发出消息
        applicationContext.publishEvent(new TagDeleteEvent(this, tag.getTagId()));
    }

    @Override
    public IPage<LocationDTO> pageList(LocationPageParam pageParam) {
        IPage<Location> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        String locationName = pageParam.getLocationName();
        LambdaQueryWrapper<Location> queryWrapper = null;
        if (StrUtil.isNotBlank(locationName)) {
            queryWrapper = new QueryWrapper<Location>().lambda().likeRight(Location::getLocationName, locationName);
        }
        super.page(page, queryWrapper);
        List<Location> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return new Page<>();
        }
        return page.convert(LocationDTO::covertFromTag);
    }

    @Override
    public List<LocationDTO> getAll() {
        List<Location> list = this.list();
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(LocationDTO::covertFromTag).collect(Collectors.toList());
        }
        // todo 怎么去同步数据
        List<QywxTagDTO> tags = qywxManager.getAllTags();
        if (CollUtil.isEmpty(tags)) {
            return CollUtil.newArrayList();
        }
        return tags.stream().map(LocationDTO::covertFromQywxTagDTO).collect(Collectors.toList());
    }

    @Override
    public void addTagMembers(TagMembersParam param) {
        qywxManager.addTagMembers(param);
    }

    @Override
    public void delTagMembers(TagMembersParam param) {
        qywxManager.delTagMembers(param);
    }

    @Override
    public List<Location> getTagListForJob(String taskId) {
        List<Location> locationList = new ArrayList<>();
        List<TaskRelLocation> trtList =
                taskRelLocationMapper.selectList(new QueryWrapper<TaskRelLocation>().lambda().eq(TaskRelLocation::getTaskId, taskId));
        if (CollUtil.isNotEmpty(trtList)) {
            List<Integer> tagIdList = trtList.stream().map(TaskRelLocation::getLocationId).collect(Collectors.toList());
            locationList = super.listByIds(tagIdList);
        }
        return locationList;
    }
}
