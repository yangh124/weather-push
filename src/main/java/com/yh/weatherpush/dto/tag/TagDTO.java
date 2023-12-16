package com.yh.weatherpush.dto.tag;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.yh.weatherpush.dto.qywx.QywxTagDTO;
import com.yh.weatherpush.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : yh
 * @date : 2022/3/6 14:32
 */
@Data
public class TagDTO implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Integer id;

    @Schema(description = "企业微信tag_name（城市name）")
    private String tagName;

    @Schema(description = "地区编码")
    private String code;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime ctime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "修改时间")
    private LocalDateTime utime;

    public static TagDTO covertFromQywxTagDTO(QywxTagDTO qywxTagDTO) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(qywxTagDTO.getTagId());
        tagDTO.setTagName(qywxTagDTO.getTagName());
        return tagDTO;
    }

    public static TagDTO covertFromTag(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setTagName(tag.getTagName());
        tagDTO.setCode(tag.getCode());
        tagDTO.setCtime(tag.getCtime());
        tagDTO.setUtime(tag.getUtime());
        return tagDTO;
    }
}
