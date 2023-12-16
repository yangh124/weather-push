package com.yh.weatherpush.dto.qywx.request;

import com.alibaba.fastjson2.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.TextDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 13:18
 */
@Data
@Builder
public class TextMsgReqDTO implements Serializable {

    private static final long serialVersionUID = 5442705499645354230L;

    /**
     * 指定接收消息的成员
     */
    @JSONField(name = "totag")
    private Integer toTag;

    /**
     * 消息类型
     */
    @JSONField(name = "msgtype")
    private String msgType;

    /**
     * 企业id
     */
    @JSONField(name = "agentid")
    private String agentId;

    /**
     * 消息内容
     */
    private TextDTO text;
}

