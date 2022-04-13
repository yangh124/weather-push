package com.yh.weatherpush.schdule;

import com.yh.weatherpush.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author : yh
 * @date : 2021/10/31 15:12
 */
@Slf4j
@Component
public class ScheduledTask implements SchedulingConfigurer {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 设置线程池
        taskRegistrar.setScheduler(taskScheduler());
        // 添加任务
        taskRegistrar.addTriggerTask(new TriggerTask(scheduleTaskService::scheduledTask1,
            triggerContext -> new CronTrigger("0 10 7 * * ?").nextExecutionTime(triggerContext)));
        taskRegistrar.addTriggerTask(new TriggerTask(scheduleTaskService::scheduledTask2,
            triggerContext -> new CronTrigger("0 5 8 * * ?").nextExecutionTime(triggerContext)));
        taskRegistrar.addTriggerTask(new TriggerTask(scheduleTaskService::scheduledTask3,
            triggerContext -> new CronTrigger("0 30 20 * * ?").nextExecutionTime(triggerContext)));
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
