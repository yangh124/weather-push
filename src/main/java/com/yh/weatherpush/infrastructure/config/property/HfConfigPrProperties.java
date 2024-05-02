package com.yh.weatherpush.infrastructure.config.property;

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
@ConfigurationProperties(prefix = "hf-weather")
public class HfConfigPrProperties  {

    private String key;

    private String baseUrl;

    /**
     * 查询城市code
     */
    private String cityUrl;


}
