package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class GetJoinQrCodeRespDTO extends QywxRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1439710310400543515L;

    @JSONField(name = "join_qrcode")
    private String joinQrcode;

}
