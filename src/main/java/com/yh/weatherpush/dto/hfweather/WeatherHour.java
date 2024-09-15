package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/11/2 21:50
 */
@Data
public class WeatherHour implements Serializable {

    @Serial
    private static final long serialVersionUID = 8793942693309600365L;
    /**
     * 预报时间
     */
    private String fxTime;

    /**
     * 温度，默认单位：摄氏度
     */
    private String temp;

    /**
     * 天气状况和图标的代码，图标可通过天气状况和图标下载
     */
    private String icon;

    /**
     * 天气状况的文字描述，包括阴晴雨雪等天气状态的描述
     */
    private String text;

    /**
     * 风向360角度
     */
    private String wind360;

    /**
     * 风向
     */
    private String windDir;

    /**
     * 风力等级
     */
    private String windScale;

    /**
     * 风速，公里/小时
     */
    private String windSpeed;

    /**
     * 相对湿度，百分比数值
     */
    private String humidity;

    /**
     * 当前小时累计降水量，默认单位：毫米
     */
    private String precip;

    /**
     * 逐小时预报降水概率，百分比数值，可能为空
     */
    private String pop;

    /**
     * 大气压强，默认单位：百帕
     */
    private String pressure;

    /**
     * 云量，百分比数值
     */
    private String cloud;

    /**
     * 露点温度
     */
    private String dew;
}
