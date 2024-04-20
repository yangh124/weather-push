package com.yh.weatherpush.dto.tag;

import com.yh.weatherpush.dto.PageParam;
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
