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
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.entity.TaskRelTag;
import com.yh.weatherpush.enums.TaskEnum;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.manager.TaskRelTagManager;
import com.yh.weatherpush.manager.mapstruct.ISchTaskMapper;
import com.yh.weatherpush.mapper.SchTaskMapper;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.quartz.QuartzClient;
import com.yh.weatherpush.service.SchTaskService;
import org.quartz.Scheduler;
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
    private TagMapper tagMapper;
    @Autowired
    private TaskRelTagManager taskRelTagManager;

    @Override
    public IPage<SchTaskPageDTO> pageList(PageParam pageParam) {
        IPage<SchTask> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = super.page(page);
        if (page.getTotal() == 0) {
            return new Page<>();
        }
        List<SchTask> records = page.getRecords();
        List<Integer> taskIds = records.stream().map(SchTask::getId).collect(Collectors.toList());
        Map<Integer, List<Tag>> map = getTaskTagMap(taskIds);
        return page.convert(record -> {
            SchTaskPageDTO dto = ISchTaskMapper.INSTANCE.toSchTaskPageDTO(record);
            Integer taskId = record.getId();
            List<Tag> tagList = map.get(taskId);
            dto.setTagList(tagList);
            String taskName = record.getTaskName();
            String desc = TaskEnum.getDescByName(taskName);
            dto.setTaskName(desc);
            return dto;
        });
    }


    private Map<Integer, List<Tag>> getTaskTagMap(List<Integer> taskIds) {
        Map<Integer, List<Tag>> map = new HashMap<>(taskIds.size());
        // 此处待优化
        for (Integer taskId : taskIds) {
            List<TaskRelTag> list =
                    taskRelTagManager.list(new QueryWrapper<TaskRelTag>().lambda().eq(TaskRelTag::getTaskId, taskId));
            if (CollUtil.isNotEmpty(list)) {
                List<Integer> tagIds = list.stream().map(TaskRelTag::getTagId).collect(Collectors.toList());
                List<Tag> tagList = tagMapper.selectBatchIds(tagIds);
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
        List<Integer> tagIds = param.getTagIds();
        addBatch(taskId, tagIds);
        QuartzBean quartzBean = ISchTaskMapper.INSTANCE.toQuartzBean(schTask);
        quartzBean.setId(String.valueOf(taskId));
        quartzClient.create(scheduler, quartzBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        super.removeById(id);
        quartzClient.delete(scheduler, String.valueOf(id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(Integer id, UpdateTaskDTO dto) {
        SchTask task = super.getById(id);
        Integer status = dto.getStatus();
        String cronExp = dto.getCronExp();
        List<Integer> tagIds = dto.getTagIds();
        if (ObjectUtil.isNull(status) || StrUtil.isBlank(cronExp) || CollUtil.isEmpty(tagIds)) {
            throw new ApiException("参数错误!");
        }
        task = ISchTaskMapper.INSTANCE.toSchTaskFromUpdate(dto);
        super.updateById(task);
        taskRelTagManager.remove(new UpdateWrapper<TaskRelTag>().lambda().eq(TaskRelTag::getTaskId, id));
        addBatch(id, tagIds);

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

    private void addBatch(Integer id, List<Integer> tagIds) {
        List<TaskRelTag> list = new ArrayList<>(tagIds.size());
        for (Integer tagId : tagIds) {
            TaskRelTag taskRelTag = new TaskRelTag();
            taskRelTag.setTaskId(id);
            taskRelTag.setTagId(tagId);
            taskRelTag.setCtime(LocalDateTime.now());
            list.add(taskRelTag);
        }
        taskRelTagManager.saveBatch(list);
    }

}
