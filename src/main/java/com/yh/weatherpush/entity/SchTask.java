package com.yh.weatherpush.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author yh
 * @since 2022-04-17
 */
@Getter
@Setter
@TableName("sys_sch_task")
@ApiModel(value = "SchTask对象", description = "定时任务")
public class SchTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(serializeUsing = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("cron表达式")
    private String cronExp;

    @ApiModelProperty("任务描述")
    private String taskDesc;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private LocalDateTime utime;

    @ApiModelProperty("0-启动 1-暂停")
    private Integer status;
}
