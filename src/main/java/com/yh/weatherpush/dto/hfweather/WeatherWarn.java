package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/11/5 22:40
 */
@Data
public class WeatherWarn implements Serializable {

    private static final long serialVersionUID = 3572715887987263852L;

    private String id;

    private String sender;

    private String pubTime;

    private String title;

    private String startTime;

    private String endTime;

    private String status;

    private String level;

    private String type;

    private String typeName;

    private String text;

    private String related;
}
