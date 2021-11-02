package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/2 21:54
 */
@Data
public class HfWeatherHourResp implements Serializable {
    private static final long serialVersionUID = 6897882359003991958L;

    private String code;

    /**
     * 当前API的最近更新时间
     */
    private String updateTime;

    /**
     * 当前数据的响应式页面，便于嵌入网站或应用
     */
    private String fxLink;

    /**
     * 逐小时天气
     */
    private List<WeatherHour> hourly;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;
}
