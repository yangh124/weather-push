package com.yh.weatherpush.service;


import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.dto.qxwx.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 14:16
 */
public interface WeatherService {

    /**
     * 获取今日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getTodayWeather(List<TagLocation> tags);

    /**
     *  获取明日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getTomWeather(List<TagLocation> tags);


    /**
     * 通过地名查询 location  code
     *
     * @param cityList
     * @return {椒江=101210101, 嘉定=101210101, 杭州=101210101, 开化=101210101}
     */
    Map<Integer, String> getLocations(List<Tag> cityList);


    /**
     *  获取天气灾害预警
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getWeatherWarn(List<TagLocation> tags);
}
