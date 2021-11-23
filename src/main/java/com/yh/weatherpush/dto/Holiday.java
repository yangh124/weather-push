package com.yh.weatherpush.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author : yh
 * @date : 2021/11/23 20:08
 */
@Data
public class Holiday implements Serializable {

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date;

    /**
     * 节假日名称
     */
    private String name;

    /**
     * 是否休息
     */
    private boolean isOffDay;
}
