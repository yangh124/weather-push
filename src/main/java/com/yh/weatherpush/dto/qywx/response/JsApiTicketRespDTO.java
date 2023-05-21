package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JsApiTicketRespDTO extends QywxBaseRespDTO {

    private static final long serialVersionUID = 8516456470682548057L;

    /**
     * 生成签名所需的jsapi_ticket，最长为512字节
     */
    private String ticket;

    /**
     * 过期时间（秒）
     */
    @JSONField(name = "expires_in")
    private Long expiresIn;


}
