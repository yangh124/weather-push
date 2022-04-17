package com.yh.weatherpush.schdule;

import cn.hutool.core.collection.CollUtil;
import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author : yh
 * @date : 2021/10/31 15:12
 */
@Slf4j
@Component
public class ScheduledTask implements SchedulingConfigurer {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 设置线程池
        taskRegistrar.setScheduler(taskScheduler());
        // 添加任务
        Map<String, SchTask> schTaskMap = redisService.redisSchTask();
        if (CollUtil.isNotEmpty(schTaskMap)) {
            for (String s : schTaskMap.keySet()) {
                SchTask schTask = schTaskMap.get(s);
                taskRegistrar.addCronTask(() -> {
                    try {
                        Method method = scheduleTaskService.getClass().getMethod(s);
                        method.invoke(scheduleTaskService);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        log.error(e.getMessage(), e);
                    }
                }, schTask.getCronExp());
            }
        }
    }

    /**
     * 定义线程池
     * 
     * @return 线程池
     */
    private ScheduledThreadPoolExecutor taskScheduler() {
        return new ScheduledThreadPoolExecutor(10, r -> new Thread(r, "定时任务线程-" + Thread.currentThread().getId()));
    }

}
