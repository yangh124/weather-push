package com.yh.weatherpush.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author : yh
 * @date : 2022/4/18 21:27
 */
@Component
public class WeatherTomorrowJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // todo
    }
}
