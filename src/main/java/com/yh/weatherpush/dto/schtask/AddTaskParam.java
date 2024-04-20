package com.yh.weatherpush.dto.schtask;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/17 16:43
 */
@Data
public class AddTaskParam implements Serializable {

    @Serial
    private static final long serialVersionUID = -2918537822083452455L;

    @NotNull(message = "任务名称不能为空")
    @Schema(description = "任务名称")
    private String taskName;

    @NotNull(message = "cron表达式不能为空")
    @Schema(description = "cron表达式")
    private String cronExp;

    @Schema(description = "任务描述")
    private String taskDesc;

    @NotEmpty(message = "关联地区不能为空")
    @Schema(description = "地区id")
    private List<Integer> locationIds;

}
