package com.yh.weatherpush.quartz;

import cn.hutool.extra.spring.SpringUtil;
import com.yh.weatherpush.dto.QuartzBean;
import com.yh.weatherpush.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * quartz工具类
 *
 * @author : yh
 * @date : 2022/4/19 20:17
 */
@Slf4j
@Component
public class QuartzClient {

    /**
     * 创建job
     *
     * @param scheduler  调度器
     * @param quartzBean 任务信息
     */
    public void create(Scheduler scheduler, QuartzBean quartzBean) {
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
     * @param scheduler 调度器
     * @param taskId    任务信息
     */
    public void stop(Scheduler scheduler, String taskId) {
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
     * @param scheduler 调度器
     * @param taskId    任务信息
     */
    public void start(Scheduler scheduler, String taskId) {
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
     * @param scheduler  调度器
     * @param quartzBean 任务信息
     */
    public void update(Scheduler scheduler, QuartzBean quartzBean) {
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
     * @param scheduler 调度器
     * @param taskId    任务id
     */
    public void delete(Scheduler scheduler, String taskId) {
        try {
            JobKey jobKey = JobKey.jobKey(taskId);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new ApiException(e);
        }
    }
}
