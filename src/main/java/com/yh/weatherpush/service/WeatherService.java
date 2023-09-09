package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 14:16
 */
public interface WeatherService {

    /**
     * 获取今日天气
     *
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Long, String> getTodayWeather(List<Tag> tags);

    /**
     * 获取明日天气
     *
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Long, String> getTomWeather(List<Tag> tags);

    /**
     * 获取redis天气
     *
     * @param tags
     * @return
     */
    Map<Long, String> getRedisWeather(List<Tag> tags);

    /**
     * 通过地名查询 location code
     *
     * @param name 地名
     * @return 杭州=10121010
     */
    String getLocation(String name);
}
