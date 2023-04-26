package com.yh.weatherpush.dto.qywx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class QywxTagDTO implements Serializable {

    private static final long serialVersionUID = 1141658194181269440L;

    @JSONField(name = "tagid")
    private Integer tagId;

    @JSONField(name = "tagname")
    private String tagName;
}
