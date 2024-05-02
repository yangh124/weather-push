package com.yh.weatherpush.infrastructure.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.infrastructure.mapper.TaskRelLocationMapper;
import com.yh.weatherpush.infrastructure.po.TaskRelLocation;
import org.springframework.stereotype.Component;

@Component
public class TaskRelLocationManager extends ServiceImpl<TaskRelLocationMapper, TaskRelLocation> {

}
