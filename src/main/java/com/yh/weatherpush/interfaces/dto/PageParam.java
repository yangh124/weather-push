package com.yh.weatherpush.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/6 14:36
 */
@Getter
@Setter
public class PageParam implements Serializable {

    @Serial
    private static final long serialVersionUID = -5036818331610124320L;

    @Schema(description = "当前页")
    private Integer currentPage = 1;

    @Schema(description = "每页大小")
    private Integer pageSize = 10;
}
