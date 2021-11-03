package com.yh.weatherpush.service;


import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.dto.qxwx.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 14:16
 */
public interface GetWeatherService {


    /**
     * 今日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> geTodayWeather(List<TagLocation> tags);

    /**
     * 明日天气
     * @param tags 1 : 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> geTomWeather(List<TagLocation> tags);


    /**
     * 通过地名查询 location  code
     *
     * @param cityList
     * @return {椒江=101210101, 嘉定=101210101, 杭州=101210101, 开化=101210101}
     */
    Map<Integer, String> getLocations(List<Tag> cityList);



}
