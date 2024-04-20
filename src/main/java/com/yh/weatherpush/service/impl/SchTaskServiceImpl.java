package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.QuartzBean;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.entity.Location;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.TaskRelLocation;
import com.yh.weatherpush.enums.TaskEnum;
import com.yh.weatherpush.event.*;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.manager.TaskRelLocationManager;
import com.yh.weatherpush.manager.mapstruct.ISchTaskMapper;
import com.yh.weatherpush.mapper.LocationMapper;
import com.yh.weatherpush.mapper.SchTaskMapper;
import com.yh.weatherpush.service.SchTaskService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-04-17
 */
@AllArgsConstructor
@Service
public class SchTaskServiceImpl extends ServiceImpl<SchTaskMapper, SchTask> implements SchTaskService {

    private final LocationMapper locationMapper;
    private final TaskRelLocationManager taskRelLocationManager;
    private final ApplicationContext applicationContext;

    @Override
    public IPage<SchTaskPageDTO> pageList(PageParam pageParam) {
        IPage<SchTask> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = super.page(page);
        if (page.getTotal() == 0) {
            return new Page<>();
        }
        List<SchTask> records = page.getRecords();
        List<Integer> taskIds = records.stream().map(SchTask::getId).collect(Collectors.toList());
        Map<Integer, List<Location>> map = getTaskTagMap(taskIds);
        return page.convert(record -> {
            SchTaskPageDTO dto = ISchTaskMapper.INSTANCE.toSchTaskPageDTO(record);
            Integer taskId = record.getId();
            List<Location> locationList = map.get(taskId);
            dto.setLocationList(locationList);
            String taskName = record.getTaskName();
            String desc = TaskEnum.getDescByName(taskName);
            dto.setTaskName(desc);
            return dto;
        });
    }


    private Map<Integer, List<Location>> getTaskTagMap(List<Integer> taskIds) {
        Map<Integer, List<Location>> map = new HashMap<>(taskIds.size());
        // 此处待优化
        for (Integer taskId : taskIds) {
            List<TaskRelLocation> list =
                    taskRelLocationManager.list(new QueryWrapper<TaskRelLocation>().lambda().eq(TaskRelLocation::getTaskId, taskId));
            if (CollUtil.isNotEmpty(list)) {
                List<Integer> tagIds = list.stream().map(TaskRelLocation::getLocationId).collect(Collectors.toList());
                List<Location> tagList = locationMapper.selectBatchIds(tagIds);
                map.put(taskId, tagList);
            }
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(AddTaskParam param) {
        SchTask schTask = ISchTaskMapper.INSTANCE.toSchTaskFromAdd(param);
        schTask.setCtime(LocalDateTime.now());
        super.save(schTask);
        Integer taskId = schTask.getId();
        List<Integer> locationIds = param.getLocationIds();
        addBatch(taskId, locationIds);
        QuartzBean quartzBean = ISchTaskMapper.INSTANCE.toQuartzBean(schTask);
        quartzBean.setId(String.valueOf(taskId));
        QuartzCreateEvent quartzCreateEvent = new QuartzCreateEvent(quartzBean);
        applicationContext.publishEvent(quartzCreateEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        super.removeById(id);
        QuartzDeleteEvent quartzDeleteEvent = new QuartzDeleteEvent(String.valueOf(id));
        applicationContext.publishEvent(quartzDeleteEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Integer id, UpdateTaskDTO dto) {
        Integer status = dto.getStatus();
        String cronExp = dto.getCronExp();
        List<Integer> tagIds = dto.getLocationIds();
        if (ObjectUtil.isNull(status) || StrUtil.isBlank(cronExp) || CollUtil.isEmpty(tagIds)) {
            throw new ApiException("参数错误!");
        }
        SchTask task = ISchTaskMapper.INSTANCE.toSchTaskFromUpdate(dto);
        task.setId(id);
        super.updateById(task);
        taskRelLocationManager.remove(new UpdateWrapper<TaskRelLocation>().lambda().eq(TaskRelLocation::getTaskId, id));
        addBatch(id, tagIds);

        if (StrUtil.isNotBlank(cronExp)) {
            QuartzBean quartzBean = new QuartzBean();
            quartzBean.setId(String.valueOf(id));
            quartzBean.setCronExp(cronExp);
            QuartzUpdateEvent quartzUpdateEvent = new QuartzUpdateEvent(quartzBean);
            applicationContext.publishEvent(quartzUpdateEvent);
        }
        if (ObjectUtil.isNotNull(status)) {
            String idStr = String.valueOf(id);
            if (0 == status) {
                QuartzStartEvent quartzStartEvent = new QuartzStartEvent(idStr);
                applicationContext.publishEvent(quartzStartEvent);
            } else {
                QuartzStopEvent quartzStopEvent = new QuartzStopEvent(idStr);
                applicationContext.publishEvent(quartzStopEvent);
            }
        }
    }

    private void addBatch(Integer id, List<Integer> locationIds) {
        List<TaskRelLocation> list = new ArrayList<>(locationIds.size());
        for (Integer locationId : locationIds) {
            TaskRelLocation taskRelLocation = new TaskRelLocation();
            taskRelLocation.setTaskId(id);
            taskRelLocation.setLocationId(locationId);
            taskRelLocation.setCtime(LocalDateTime.now());
            list.add(taskRelLocation);
        }
        taskRelLocationManager.saveBatch(list);
    }

}
