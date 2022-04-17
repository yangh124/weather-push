package com.yh.weatherpush.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/6 14:36
 */
@Getter
@Setter
public class PageParam implements Serializable {

    private static final long serialVersionUID = -5036818331610124320L;

    @ApiModelProperty("当前页")
    private Integer currentPage = 1;

    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
}
