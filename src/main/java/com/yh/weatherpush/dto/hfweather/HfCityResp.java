package com.yh.weatherpush.dto.hfweather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/10/31 18:12
 */
@Data
public class HfCityResp implements Serializable {

    @Serial
    private static final long serialVersionUID = -1512465738596073807L;

    private String code;

    private List<LocationDTO> location;

    /**
     * 当前天气来源
     */
    private WeatherSource refer;
}
