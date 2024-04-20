package com.yh.weatherpush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.entity.TaskRelLocation;
import com.yh.weatherpush.mapper.TaskRelLocationMapper;
import com.yh.weatherpush.service.TaskRelTagService;
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
