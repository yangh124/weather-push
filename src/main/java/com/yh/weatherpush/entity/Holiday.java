package com.yh.weatherpush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 节假日
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@Getter
@Setter
@TableName("sys_holiday")
@ApiModel(value = "Holiday对象", description = "节假日")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("节假日名称")
    private String holidayName;

    @ApiModelProperty("日期")
    private LocalDate holidayDate;

    @ApiModelProperty("是否放假（0-补班 1-放假）")
    private Boolean isOffDay;

    @ApiModelProperty("所在年份")
    private String year;

    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @ApiModelProperty("修改时间")
    private LocalDateTime utime;

    @ApiModelProperty("0-正常 1-删除")
    @TableLogic
    private Boolean isDelete;

}
