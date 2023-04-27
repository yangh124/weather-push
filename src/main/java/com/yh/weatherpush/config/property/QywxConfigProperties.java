package com.yh.weatherpush.config.property;

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
public class QywxConfigProperties {

    private String corpid;

    private String pushSecret;

    private String otherSecret;

    private String agentid;
}
