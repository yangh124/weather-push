package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 15:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetTokenRespDTO extends QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 5240530319050183669L;

    /**
     * 获取到的凭证,最长为512字节
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 凭证的有效时间（秒）
     */
    @JSONField(name = "expires_in")
    private Integer expiresIn;
}
