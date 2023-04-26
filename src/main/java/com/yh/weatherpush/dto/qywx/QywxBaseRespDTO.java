package com.yh.weatherpush.dto.qywx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 7090003879045196262L;

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "errmsg")
    private String errMsg;
}
