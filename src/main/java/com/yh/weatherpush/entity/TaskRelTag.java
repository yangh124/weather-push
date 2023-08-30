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
 * 任务关联标签
 * </p>
 *
 * @author yh
 * @since 2023-08-30
 */
@Getter
@Setter
@TableName("sys_task_rel_tag")
public class TaskRelTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 修改时间
     */
    private LocalDateTime utime;
}
