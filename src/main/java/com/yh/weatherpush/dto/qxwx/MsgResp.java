package com.yh.weatherpush.dto.qxwx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 13:24
 */
@Data
public class MsgResp implements Serializable {

    private static final long serialVersionUID = -7488177688697508538L;
    /**
     * 返回码
     */
    private Integer errcode;

    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;

    /**
     * 不合法的userid，不区分大小写，统一转为小写
     */
    private String invaliduser;

    /**
     * 不合法的partyid
     */
    private String invalidparty;

    /**
     * 不合法的标签id
     */
    private String invalidtag;

    /**
     * 消息id
     */
    private String msgid;

    /**
     * 仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用response_code调用更新模版卡片消息接口，24小时内有效，且只能使用一次
     */
    private String response_code;

}
