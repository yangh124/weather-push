package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 14:17
 */
@Data
public class HfWeatherResp implements Serializable {

    @Serial
    private static final long serialVersionUID = -7631395513502789164L;
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
     * 当前天气
     */
    private WeatherNow now;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;
}
