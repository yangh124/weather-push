package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/5 22:39
 */
@Data
public class HfWeatherWarnResp implements Serializable {

    private static final long serialVersionUID = -4052389515435736746L;

    private String code;

    private String updateTime;

    private String fxLink;

    /**
     * 预警信息
     */
    private List<WeatherWarn> warning;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;


}
