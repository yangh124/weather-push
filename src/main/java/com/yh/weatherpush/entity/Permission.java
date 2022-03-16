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
 * 后台用户权限表
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
@Getter
@Setter
@TableName("sys_permission")
@ApiModel(value = "Permission对象", description = "后台用户权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父级权限id")
    private Long pid;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("权限值")
    private String value;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    @ApiModelProperty("前端资源路径")
    private String uri;

    @ApiModelProperty("启用状态；0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("排序")
    private Integer sort;


}
