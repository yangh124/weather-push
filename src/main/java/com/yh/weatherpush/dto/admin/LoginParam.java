package com.yh.weatherpush.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/17 20:59
 */
@Data
@Schema(description = "登录参数")
public class LoginParam implements Serializable {

    @Serial
    private static final long serialVersionUID = -5842572793711251190L;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

}
