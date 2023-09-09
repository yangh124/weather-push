package com.yh.weatherpush.dto.tag;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/2/26 14:04
 */
@Data
public class AddTagParam implements Serializable {

    private static final long serialVersionUID = 5108658370936176324L;

    @ApiModelProperty(value = "标签id")
    private Long tagId;

    @NotBlank
    @ApiModelProperty(value = "标签名字")
    private String tagName;
}
