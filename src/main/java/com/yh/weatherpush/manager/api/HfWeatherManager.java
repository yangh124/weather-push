package com.yh.weatherpush.manager.api;

import com.yh.weatherpush.config.property.HfConfigPrProperties;
import com.yh.weatherpush.dto.hfweather.HfCityResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherDayResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherHourResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherIndexResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HfWeatherManager {

    @Value("${holiday.url}")
    private String holidayUrl;
    @Autowired
    private HfConfigPrProperties hfConfig;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取节假日信息
     *
     * @param year 年份
     * @return 节假日信息
     */
    public String getHolidayFromGitHub(Integer year) {
        String url = holidayUrl.replace("{year}", String.valueOf(year));
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        if (forEntity.getStatusCode().is2xxSuccessful()) {
            return forEntity.getBody();
        }
        return null;
    }

    /**
     * 获取实时天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    public HfWeatherResp getRealTimeWeather(String code) {
        String weatherUrl = hfConfig.getGetUrl();
        weatherUrl = weatherUrl.replace("code", code);
        ResponseEntity<HfWeatherResp> weatherResponse = restTemplate.getForEntity(weatherUrl, HfWeatherResp.class);
        return weatherResponse.getBody();
    }

    /**
     * 获取天气指数
     *
     * @param code 地区编码code
     * @return 天气指数
     */
    public HfWeatherIndexResp getWeatherIndex(String code) {
        String weatherIndexUrl = hfConfig.getIndexUrl();
        weatherIndexUrl = weatherIndexUrl.replace("code", code);
        ResponseEntity<HfWeatherIndexResp> weatherIndexResponse =
                restTemplate.getForEntity(weatherIndexUrl, HfWeatherIndexResp.class);
        return weatherIndexResponse.getBody();
    }

    /**
     * 获取24小时天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    public HfWeatherHourResp getHourWeather(String code) {
        String weatherHourUrl = hfConfig.getHourUrl();
        weatherHourUrl = weatherHourUrl.replace("code", code);
        ResponseEntity<HfWeatherHourResp> weatherHourResponse =
                restTemplate.getForEntity(weatherHourUrl, HfWeatherHourResp.class);
        return weatherHourResponse.getBody();
    }

    /**
     * 获取三天天气
     *
     * @param code 地区编码code
     * @return 天气信息
     */
    public HfWeatherDayResp getDayWeather(String code) {
        String url = hfConfig.getDayUrl();
        url = url.replace("code", code);
        ResponseEntity<HfWeatherDayResp> weatherResponse = restTemplate.getForEntity(url, HfWeatherDayResp.class);
        return weatherResponse.getBody();
    }

    /**
     * 获取地区code
     *
     * @param name 地区名称
     * @return 地区信息
     */
    public HfCityResp getLocation(String name) {
        String cityUrl = hfConfig.getCityUrl();
        cityUrl = cityUrl.replace("cityName", name);
        ResponseEntity<HfCityResp> response = restTemplate.getForEntity(cityUrl, HfCityResp.class);
        return response.getBody();
    }
}
