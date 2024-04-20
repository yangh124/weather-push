package com.yh.weatherpush.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 节假日
 * </p>
 *
 * @author yh
 * @since 2023-09-09
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节假日名称
     */
    private String holidayName;

    /**
     * 日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private LocalDate holidayDate;

    /**
     * 是否放假（0-补班 1-放假）
     */
    private Boolean isOffDay;

    /**
     * 所在年份
     */
    private String year;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 修改时间
     */
    private LocalDateTime utime;

    /**
     * 0-正常 1-删除
     */
    @TableLogic
    private Integer isDelete;
}
