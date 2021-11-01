package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 天气来源
 *
 * @author : yh
 * @date : 2021/10/31 14:24
 */
@Data
public class WeatherSource implements Serializable {

    /**
     * 原始数据来源，或数据源说明，可能为空
     */
    private List<String> sources;

    /**
     * 数据许可或版权声明，可能为空
     */
    private List<String> license;
}
