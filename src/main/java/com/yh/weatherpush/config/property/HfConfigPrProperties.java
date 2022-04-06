package com.yh.weatherpush.config.property;

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
public class HfConfigPrProperties  {

    private String key;

    /**
     * 实时天气
     */
    private String getUrl;

    /**
     * 查询城市code
     */
    private String cityUrl;

    /**
     * 未来24h天气
     */
    private String hourUrl;

    /**
     * 天气指数
     */
    private String indexUrl;

    /**
     * 天气预警
     */
    private String warnUrl;

    /**
     * 3天天气
     */
    private String dayUrl;
}
