package com.yh.weatherpush.manager.api;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hf-weather-api", url = "${hf-weather.base-url}")
public interface HfWeatherApiClient extends com.yh.api.client.HfWeatherApiClient {

}
