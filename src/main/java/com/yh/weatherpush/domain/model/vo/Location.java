package com.yh.weatherpush.domain.model.vo;

import lombok.Data;

@Data
public class Location {

    /**
     * 地区名称
     */
    private String locationName;

    /**
     * 地区编码
     */
    private String code;
}
