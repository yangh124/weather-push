package com.yh.weatherpush.dto.qywx;

import lombok.Data;

import java.io.Serializable;

@Data
public class QywxAgentConfigDTO implements Serializable {

    /**
     * 企业微信的corpid
     */
    private String corpid;

    /**
     * 企业微信的应用id
     */
    private String agentid;

    /**
     * 生成签名的时间戳
     */
    private Long timestamp;

    /**
     * 生成签名的随机串
     */
    private String nonceStr;

    /**
     * 签名
     */
    private String signature;
}
