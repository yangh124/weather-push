package com.yh.weatherpush.domain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.application.service.TaskRelTagService;
import com.yh.weatherpush.infrastructure.mapper.TaskRelLocationMapper;
import com.yh.weatherpush.infrastructure.po.TaskRelLocation;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务关联标签 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-04-25
 */
@Service
public class TaskRelTagServiceImpl extends ServiceImpl<TaskRelLocationMapper, TaskRelLocation> implements TaskRelTagService {

}
