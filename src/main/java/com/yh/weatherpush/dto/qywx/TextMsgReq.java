package com.yh.weatherpush.dto.qywx;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 13:18
 */
@Data
@Builder
public class TextMsgReq implements Serializable {

    private static final long serialVersionUID = 5442705499645354230L;
    /**
     * 指定接收消息的成员
     */
    private Integer totag;

    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * 企业id
     */
    private String agentid;

    /**
     * 消息内容
     */
    private Text text;
}

