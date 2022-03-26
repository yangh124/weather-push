package com.yh.weatherpush.schdule;

import com.yh.weatherpush.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
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
        taskRegistrar.setScheduler(taskScheduler());
        taskRegistrar.addCronTask(scheduleTaskService::scheduledTask1, "0 10 7 * * ?");
        taskRegistrar.addCronTask(scheduleTaskService::scheduledTask2, "0 5 8 * * ?");
        taskRegistrar.addCronTask(scheduleTaskService::scheduledTask3, "0 30 20 * * ?");
        // taskRegistrar.addCronTask(scheduleTaskService::scheduledTask4, "0 0 0/1 * * ?");
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
