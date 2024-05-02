package com.yh.weatherpush.interfaces.dto.qywx;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class QywxRespDTO {

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "errmsg")
    private String errMsg;

}
