package com.yh.weatherpush.infrastructure.manager;

import cn.hutool.extra.spring.SpringUtil;
import com.yh.weatherpush.infrastructure.exception.ApiException;
import com.yh.weatherpush.interfaces.dto.QuartzBean;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * quartz工具类
 *
 * @author : yh
 * @date : 2022/4/19 20:17
 */
@Slf4j
@Component
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建job
     *
     * @param quartzBean 任务信息
     */
    public void create(QuartzBean quartzBean) {
        String id = quartzBean.getId();
        String taskName = quartzBean.getTaskName();
        String cronExp = quartzBean.getCronExp();
        try {
            Job bean = SpringUtil.getBean(taskName);
            Class<? extends Job> aClass = bean.getClass();
            TriggerKey triggerKey = TriggerKey.triggerKey(id);
            JobKey jobKey = JobKey.jobKey(id);
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
            JobDetail jobDetail =
                    JobBuilder.newJob(aClass).withIdentity(jobKey).withDescription(quartzBean.getTaskDesc()).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }

    /**
     * 停止任务
     *
     * @param taskId 任务信息
     */
    public void stop(String taskId) {
        JobKey jobKey = JobKey.jobKey(taskId);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }

    /**
     * 启动任务
     *
     * @param taskId 任务信息
     */
    public void start(String taskId) {
        JobKey jobKey = JobKey.jobKey(taskId);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }

    /**
     * 修改job
     * <p>
     * 只能修改执行时间
     *
     * @param quartzBean 任务信息
     */
    public void update(QuartzBean quartzBean) {
        String id = quartzBean.getId();
        String cronExp = quartzBean.getCronExp();
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(id);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            cronTrigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }

    /**
     * 删除job
     *
     * @param taskId 任务id
     */
    public void delete(String taskId) {
        try {
            JobKey jobKey = JobKey.jobKey(taskId);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }
}
