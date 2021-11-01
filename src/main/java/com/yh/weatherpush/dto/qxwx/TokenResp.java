package com.yh.weatherpush.dto.qxwx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 15:30
 */
@Data
public class TokenResp implements Serializable {

    private static final long serialVersionUID = 5240530319050183669L;

    private String errcode;

    private String errmsg;

    private String access_token;

    private Integer expires_in;
}
