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
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.mapper.SchTaskMapper;
import com.yh.weatherpush.quartz.QuartzClient;
import com.yh.weatherpush.quartz.job.WeatherTodayJob;
import com.yh.weatherpush.service.SchTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public IPage<SchTask> pageList(PageParam pageParam) {
        IPage<SchTask> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        return super.page(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(AddTaskParam param) {
        SchTask schTask = new SchTask();
        BeanUtil.copyProperties(param, schTask);
        schTask.setCtime(LocalDateTime.now());
        super.save(schTask);
        QuartzBean quartzBean = new QuartzBean();
        BeanUtil.copyProperties(schTask, quartzBean);
        quartzBean.setId(String.valueOf(schTask.getId()));
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
