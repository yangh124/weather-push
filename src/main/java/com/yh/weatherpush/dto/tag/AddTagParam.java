package com.yh.weatherpush.dto.tag;

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
public class AddTagParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 5108658370936176324L;

    @Schema(description = "标签id")
    private Long tagId;

    @NotBlank
    @Schema(description = "标签名字")
    private String tagName;
}
