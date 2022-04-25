package com.yh.weatherpush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 任务关联标签
 * </p>
 *
 * @author yh
 * @since 2022-04-25
 */
@Getter
@Setter
@TableName("sys_task_rel_tag")
@ApiModel(value = "TaskRelTag对象", description = "任务关联标签")
public class TaskRelTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("标签id")
    private Long tagId;

    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @ApiModelProperty("修改时间")
    private LocalDateTime utime;


}
