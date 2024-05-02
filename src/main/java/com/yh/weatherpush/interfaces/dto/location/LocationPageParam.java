package com.yh.weatherpush.interfaces.dto.location;


import com.yh.weatherpush.interfaces.dto.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : yh
 * @date : 2022/4/17 16:40
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LocationPageParam extends PageParam {

    private String locationName;
}
