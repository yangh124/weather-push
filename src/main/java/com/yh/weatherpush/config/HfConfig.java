package com.yh.weatherpush.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : yh
 * @date : 2021/10/31 14:30
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "hfweather")
public class HfConfig {

    private String key;

    private String getUrl;

    private String cityUrl;

    private String pluginUrl;
}
