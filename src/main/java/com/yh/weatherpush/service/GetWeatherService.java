package com.yh.weatherpush.service;


import com.yh.weatherpush.dto.qxwx.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 14:16
 */
public interface GetWeatherService {


    /**
     * @param map 杭州 : 101210101
     * @return 杭州 : 天气信息
     */
    Map<Integer, String> getWeather(Map<Integer, String> map, Map<Integer, List<Tag>> tagMap);

    /**
     * 通过地名查询 location  code
     *
     * @param cityList
     * @return {椒江=101210101, 嘉定=101210101, 杭州=101210101, 开化=101210101}
     */
    Map<Integer, String> getLocations(List<Tag> cityList);
}
