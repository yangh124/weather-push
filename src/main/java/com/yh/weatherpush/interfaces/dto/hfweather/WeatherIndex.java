package com.yh.weatherpush.interfaces.dto.hfweather;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/11/2 21:21
 */
@Data
public class WeatherIndex implements Serializable {

    private static final long serialVersionUID = 1608198535603243897L;
    private String date;

    private String type;

    private String name;

    private String level;

    private String category;

    private String text;
}
