package com.yh.weatherpush.interfaces.dto.hfweather;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/2 21:19
 */
@Data
public class HfWeatherIndexResp implements Serializable {

    private static final long serialVersionUID = 2905581155262671962L;

    private String code;

    private String updateTime;

    private String fxLink;

    /**
     * 天气指数
     */
    private List<WeatherIndex> daily;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;

}
