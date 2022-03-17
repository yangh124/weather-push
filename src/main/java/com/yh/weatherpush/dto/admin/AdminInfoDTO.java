package com.yh.weatherpush.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/17 21:54
 */
@Data
@ApiModel(description = "管理员用户信息")
public class AdminInfoDTO implements Serializable {
    private static final long serialVersionUID = 3940779304154974361L;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;
}
