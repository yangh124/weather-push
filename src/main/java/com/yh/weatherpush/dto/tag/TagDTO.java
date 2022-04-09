package com.yh.weatherpush.dto.tag;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : yh
 * @date : 2022/3/6 14:32
 */
@Data
public class TagDTO implements Serializable {

    @JSONField(name = "id")
    @ApiModelProperty("企业微信tag_id（城市id）")
    private Integer tagId;

    @ApiModelProperty("企业微信tag_name（城市name）")
    private String tagName;

    @ApiModelProperty("地区编码")
    private String code;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private LocalDateTime utime;

}
