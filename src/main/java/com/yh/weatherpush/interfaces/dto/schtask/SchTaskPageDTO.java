package com.yh.weatherpush.interfaces.dto.schtask;

import com.yh.weatherpush.infrastructure.po.Location;
import com.yh.weatherpush.infrastructure.po.SchTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/25 21:58
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SchTaskPageDTO extends SchTask {

    @Schema(description = "地区")
    private List<Location> locationList;
}
