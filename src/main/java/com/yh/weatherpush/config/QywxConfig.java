package com.yh.weatherpush.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author : yh
 * @date : 2021/10/31 13:09
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "qywx")
public class QywxConfig {

    private String corpid;

    private String corpsecret;

    private String tokenUrl;

    private String sendUrl;

    private String agentid;

    private String labelUrl;
}
