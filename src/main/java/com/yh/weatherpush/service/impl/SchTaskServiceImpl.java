package com.yh.weatherpush.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.QuartzBean;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.entity.TaskRelTag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.mapper.SchTaskMapper;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.mapper.TaskRelTagMapper;
import com.yh.weatherpush.quartz.QuartzClient;
import com.yh.weatherpush.quartz.job.WeatherTodayJob;
import com.yh.weatherpush.service.SchTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.service.TaskRelTagService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class SchTaskServiceImpl extends ServiceImpl<SchTaskMapper, SchTask> implements SchTaskService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private QuartzClient quartzClient;
    @Autowired
    private TaskRelTagService taskRelTagService;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public IPage<SchTaskPageDTO> pageList(PageParam pageParam) {
        IPage<SchTask> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = super.page(page);
        IPage<SchTaskPageDTO> res = new Page<>();
        BeanUtil.copyProperties(page, res);
        List<SchTask> records = page.getRecords();
        List<SchTaskPageDTO> resRecords = new ArrayList<>(records.size());
        if (CollUtil.isNotEmpty(records)) {
            List<Long> taskIds = records.stream().map(SchTask::getId).collect(Collectors.toList());
            Map<Long, List<Tag>> map = getTaskTagMap(taskIds);
            for (SchTask record : records) {
                SchTaskPageDTO dto = new SchTaskPageDTO();
                BeanUtil.copyProperties(record, dto);
                Long taskId = record.getId();
                List<Tag> tagList = map.get(taskId);
                dto.setTagList(tagList);
                resRecords.add(dto);
            }
        }
        res.setRecords(resRecords);
        return res;
    }

    private Map<Long, List<Tag>> getTaskTagMap(List<Long> taskIds) {
        Map<Long, List<Tag>> map = new HashMap<>(taskIds.size());
        // 此处待优化
        for (Long taskId : taskIds) {
            List<TaskRelTag> list =
                taskRelTagService.list(new QueryWrapper<TaskRelTag>().lambda().eq(TaskRelTag::getTaskId, taskId));
            if (CollUtil.isNotEmpty(list)) {
                List<Long> tagIds = list.stream().map(TaskRelTag::getTagId).collect(Collectors.toList());
                List<Tag> tagList = tagMapper.selectBatchIds(tagIds);
                map.put(taskId, tagList);
            }
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(AddTaskParam param) {
        SchTask schTask = new SchTask();
        BeanUtil.copyProperties(param, schTask);
        schTask.setCtime(LocalDateTime.now());
        super.save(schTask);
        Long taskId = schTask.getId();
        List<Long> tagIds = param.getTagIds();
        List<TaskRelTag> list = new ArrayList<>(tagIds.size());
        for (Long tagId : tagIds) {
            TaskRelTag taskRelTag = new TaskRelTag();
            taskRelTag.setTaskId(taskId);
            taskRelTag.setTagId(tagId);
            taskRelTag.setCtime(LocalDateTime.now());
            list.add(taskRelTag);
        }
        taskRelTagService.saveBatch(list);
        QuartzBean quartzBean = new QuartzBean();
        BeanUtil.copyProperties(schTask, quartzBean);
        quartzBean.setId(String.valueOf(taskId));
        quartzClient.create(scheduler, quartzBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        super.removeById(id);
        quartzClient.delete(scheduler, String.valueOf(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Long id, UpdateTaskDTO dto) {
        SchTask task = super.getById(id);
        Integer status = dto.getStatus();
        String cronExp = dto.getCronExp();
        if (ObjectUtil.isNull(status) && StrUtil.isBlank(cronExp)) {
            throw new ApiException("参数错误!");
        }
        task.setStatus(status);
        task.setCronExp(cronExp);
        super.updateById(task);
        if (StrUtil.isNotBlank(cronExp)) {
            QuartzBean quartzBean = new QuartzBean();
            quartzBean.setId(String.valueOf(id));
            quartzBean.setCronExp(cronExp);
            quartzClient.update(scheduler, quartzBean);
        }
        if (ObjectUtil.isNotNull(status)) {
            if (0 == status) {
                quartzClient.start(scheduler, String.valueOf(id));
            } else {
                quartzClient.stop(scheduler, String.valueOf(id));
            }
        }
    }

}
