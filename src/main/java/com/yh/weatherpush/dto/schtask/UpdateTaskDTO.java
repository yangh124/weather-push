package com.yh.weatherpush.dto.schtask;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/20 20:43
 */
@Data
public class UpdateTaskDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2612048948554994025L;

    @NotNull
    @ApiModelProperty(value = "0-启动 1-停止")
    private Integer status;

    @NotBlank
    @ApiModelProperty(value = "cron表达式")
    private String cronExp;

    @ApiModelProperty("任务描述")
    private String taskDesc;

    @NotEmpty
    @ApiModelProperty("关联地区")
    private List<Integer> tagIds;
}
