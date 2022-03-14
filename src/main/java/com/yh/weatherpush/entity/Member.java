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
 * 成员表
 * </p>
 *
 * @author yh
 * @since 2022-03-14
 */
@Getter
@Setter
@TableName("sys_member")
@ApiModel(value = "Member对象", description = "成员表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("open_userid")
    private String openUserId;

    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @ApiModelProperty("修改时间")
    private LocalDateTime utime;

}
