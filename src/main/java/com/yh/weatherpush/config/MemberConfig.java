package com.yh.weatherpush.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2022/3/12 11:20
 */
@Getter
@Setter
public class MemberConfig implements Serializable {

    private static final long serialVersionUID = -7283609725180969658L;

    /**
     * 邀请加入二维码
     */
    private String joinQrcodeUrl;

}
