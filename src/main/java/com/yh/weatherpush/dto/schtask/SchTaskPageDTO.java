package com.yh.weatherpush.dto.schtask;

import com.yh.weatherpush.entity.SchTask;
import com.yh.weatherpush.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/25 21:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SchTaskPageDTO extends SchTask {

    @Schema(description = "地区")
    private List<Tag> tagList;
}
