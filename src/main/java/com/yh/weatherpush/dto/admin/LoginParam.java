package com.yh.weatherpush.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/17 20:59
 */
@Data
@ApiModel(description = "登录参数")
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -5842572793711251190L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

}
