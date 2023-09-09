package com.yh.weatherpush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author yh
 * @since 2023-09-09
 */
@Getter
@Setter
@TableName("sys_sch_task")
public class SchTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * cron表达式
     */
    private String cronExp;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 0-启动 1-暂停
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 修改时间
     */
    private LocalDateTime utime;
}
