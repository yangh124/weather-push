package com.yh.weatherpush.interfaces.dto.qywx.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author yangh
 */
@Data
public class MessageSendRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7300379492963290423L;

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    @JSONField(name = "invaliduser")
    private String invalidUser;

    @JSONField(name = "invalidparty")
    private String invalidParty;

    @JSONField(name = "invalidtag")
    private String invalidTag;

    @JSONField(name = "unlicenseduser")
    private String unlicensedUser;

    @JSONField(name = "msgid")
    private String msgId;

    @JSONField(name = "response_code")
    private String responseCode;
}
