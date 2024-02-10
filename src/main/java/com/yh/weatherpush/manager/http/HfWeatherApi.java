package com.yh.weatherpush.manager.http;

import com.yh.weatherpush.dto.hfweather.HfWeatherDayResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherHourResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherIndexResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherResp;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface HfWeatherApi {

    /**
     * 获取实时天气
     *
     * @param location 地区编码code
     * @return 天气信息
     */
    @GetExchange("/v7/weather/now")
    HfWeatherResp getRealTimeWeather(@RequestParam String location, @RequestParam String key);

    /**
     * 获取天气指数
     *
     * @param location 地区编码code
     * @return 天气指数
     */
    @GetExchange("/v7/indices/1d")
    HfWeatherIndexResp getWeatherIndex(@RequestParam Integer type, @RequestParam String location,
                                       @RequestParam String key);

    /**
     * 获取24小时天气
     *
     * @param location 地区编码code
     * @return 天气信息
     */
    @GetExchange("/v7/weather/24h")
    HfWeatherHourResp getHourWeather(@RequestParam String location, @RequestParam String key);

    /**
     * 获取三天天气
     *
     * @param location 地区编码code
     * @return 天气信息
     */
    @GetExchange("/v7/weather/3d")
    HfWeatherDayResp getDayWeather(@RequestParam String location, @RequestParam String key);
}
