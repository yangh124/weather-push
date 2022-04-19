package com.yh.weatherpush.dto;

import lombok.Data;
import org.quartz.JobDataMap;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/4/19 20:44
 */
@Data
public class QuartzBean implements Serializable {

    private static final long serialVersionUID = -230607488989139148L;

    private String id;

    private String taskName;

    private String cronExp;

    private String taskDesc;

    private JobDataMap jobDataMap;
}
