package com.yh.weatherpush.dto.tag;

import com.alibaba.fastjson2.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxTagDTO;
import com.yh.weatherpush.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : yh
 * @date : 2022/3/6 14:32
 */
@Data
public class LocationDTO implements Serializable {

    private Integer id;

    @Schema(description = "地区名称")
    private String locationName;

    @Schema(description = "地区编码")
    private String code;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime ctime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "修改时间")
    private LocalDateTime utime;

    @Schema(description = "企业微信tagId")
    private Integer tagId;

    public static LocationDTO covertFromQywxTagDTO(QywxTagDTO qywxTagDTO) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setTagId(qywxTagDTO.getTagId());
        locationDTO.setLocationName(qywxTagDTO.getTagName());
        return locationDTO;
    }

    public static LocationDTO covertFromTag(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setTagId(location.getTagId());
        locationDTO.setLocationName(location.getLocationName());
        locationDTO.setCode(location.getCode());
        locationDTO.setCtime(location.getCtime());
        locationDTO.setUtime(location.getUtime());
        return locationDTO;
    }
}
