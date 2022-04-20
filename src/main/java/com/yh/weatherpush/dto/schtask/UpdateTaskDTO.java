package com.yh.weatherpush.dto.schtask;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/4/20 20:43
 */
@Data
public class UpdateTaskDTO implements Serializable {

    private static final long serialVersionUID = 2612048948554994025L;

    @ApiModelProperty(value = "0-启动 1-停止")
    private Integer status;

    @ApiModelProperty(value = "cron表达式")
    private String cronExp;
}
