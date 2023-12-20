package com.yh.weatherpush.manager.http;

import com.yh.weatherpush.dto.hfweather.HfCityResp;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface HfGeoApi {

    @GetExchange("/v2/city/lookup?range=cn&number=1")
    HfCityResp getLocation(@RequestParam String location, @RequestParam String key);
}
