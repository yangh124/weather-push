package com.yh.weatherpush.dto.qywx.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TagCreateReqDTO implements Serializable {

    private static final long serialVersionUID = -1115049831589758080L;

    @JSONField(name = "tagid")
    private Integer tagId;

    @JSONField(name = "tagname")
    private String tagName;
}
