package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.QywxRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 15:30
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class GetTokenRespDTO extends QywxRespDTO implements Serializable {

    @Serial
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
