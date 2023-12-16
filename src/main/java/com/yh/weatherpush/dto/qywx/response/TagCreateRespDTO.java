package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author yangh
 */
@Data
public class TagCreateRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2656877935626231118L;

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    @JSONField(name = "tagid")
    private Integer tagId;
}
