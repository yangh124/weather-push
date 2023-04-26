package com.yh.weatherpush.manager.api;

import com.yh.weatherpush.dto.hfweather.HfWeatherDayResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherHourResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherIndexResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hf-weather-api", url = "${hf-weather.base-url}")
public interface HfWeatherApiClient {

    /**
     * 获取实时天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    @GetMapping("/v7/weather/now")
    HfWeatherResp getRealTimeWeather(@RequestParam String code, @RequestParam String key);

    /**
     * 获取天气指数
     *
     * @param code 地区编码code
     * @return 天气指数
     */
    @GetMapping("/v7/indices/1d")
    HfWeatherIndexResp getWeatherIndex(@RequestParam Integer type, @RequestParam String code, @RequestParam String key);

    /**
     * 获取24小时天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    @GetMapping("/v7/weather/24h")
    HfWeatherHourResp getHourWeather(@RequestParam String code, @RequestParam String key);

    /**
     * 获取三天天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    @GetMapping("/v7/weather/3d")
    HfWeatherDayResp getDayWeather(@RequestParam String code, @RequestParam String key);
}
