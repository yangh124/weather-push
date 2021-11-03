package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/3 20:44
 */
@Data
public class HfWeatherDayResp implements Serializable {
    private static final long serialVersionUID = -2357191977427239681L;

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
     * 3天天气
     */
    private List<WeatherDay> daily;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;
}
