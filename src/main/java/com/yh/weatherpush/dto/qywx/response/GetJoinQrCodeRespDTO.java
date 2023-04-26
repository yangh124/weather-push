package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetJoinQrCodeRespDTO extends QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 1439710310400543515L;

    @JSONField(name = "join_qrcode")
    private String joinQrcode;

}
