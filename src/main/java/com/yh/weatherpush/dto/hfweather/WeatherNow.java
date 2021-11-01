package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;


/**
 * @author : yh
 * @date : 2021/10/31 14:21
 */
@Data
public class WeatherNow implements Serializable {
    private static final long serialVersionUID = -5989466831882900523L;

    /**
     * 数据观测时间
     */
    private String obsTime;

    /**
     * 温度，默认单位：摄氏度
     */
    private String temp;

    /**
     * 体感温度，默认单位：摄氏度
     */
    private String feelsLike;

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
     * 大气压强，默认单位：百帕
     */
    private String pressure;

    /**
     * 能见度，默认单位：公里
     */
    private String vis;

    /**
     * 云量，百分比数值
     */
    private String cloud;

    /**
     * 露点温度
     */
    private String dew;

}
