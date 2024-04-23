package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Location;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    Map<Integer, String> getTodayWeather(List<Location> tags);

    /**
     * 获取明日天气
     *
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getTomWeather(List<Location> tags);

    /**
     * 获取redis天气
     *
     * @return
     */
    Map<Integer, String> getRedisWeather(Set<Integer> tagIds);

    /**
     * 通过地名查询 location code
     *
     * @param name 地名
     * @return 杭州=10121010
     */
    String getLocation(String name);
}
