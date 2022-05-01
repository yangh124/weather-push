package com.yh.weatherpush.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/4/30 10:46
 */
@Data
@ApiModel(description = "修改密码")
public class UpdPwdParam implements Serializable {

    private static final long serialVersionUID = 5432205822528369320L;

    @NotEmpty(message = "旧密码不能为空")
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String newPassword;

    @NotEmpty(message = "确认密码不能为空")
    @ApiModelProperty("确认密码")
    private String confirmPassword;

    @ApiModelProperty(hidden = true)
    private String username;
}
