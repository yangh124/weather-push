package com.yh.weatherpush.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/2/26 14:04
 */
@Data
public class AddLocationParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 5108658370936176324L;

    @NotBlank(message = "地区名字不能为空")
    @Schema(description = "地区名字")
    private String locationName;
}
