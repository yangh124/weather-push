package com.yh.weatherpush.domain.model;

import com.yh.weatherpush.domain.model.vo.Location;
import lombok.Data;

@Data
public class WeatherForecast {

    private Long forecastId;

    private String description;

    private Location location;
}
