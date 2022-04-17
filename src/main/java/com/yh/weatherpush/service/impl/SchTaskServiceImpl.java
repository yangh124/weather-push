package com.yh.weatherpush.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.mapper.SchTaskMapper;
import com.yh.weatherpush.service.SchTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<SchTask> pageList(PageParam pageParam) {
        IPage<SchTask> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        return super.page(page);
    }

    @Override
    public void create(AddTaskParam param) {
        SchTask schTask = new SchTask();
        BeanUtil.copyProperties(param, schTask);
        schTask.setCtime(LocalDateTime.now());
        super.save(schTask);
    }
}
