package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 18:12
 */
@Data
public class LocationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7923622735482746478L;

    private String name;
    private String id;
    private String lat;
    private String lon;
    private String adm2;
    private String adm1;
    private String country;
    private String tz;
    private String utcOffset;
    private String isDst;
    private String type;
    private String rank;
    private String fxLink;

}
