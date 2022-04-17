package com.yh.weatherpush.dto.schtask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/4/17 16:43
 */
@Data
public class AddTaskParam implements Serializable {

    private static final long serialVersionUID = -2918537822083452455L;

    @NotNull(message = "任务名称不能为空")
    @ApiModelProperty("任务名称")
    private String taskName;

    @NotNull(message = "cron表达式不能为空")
    @ApiModelProperty("cron表达式")
    private String cronExp;

    @ApiModelProperty("任务描述")
    private String taskDesc;

}
