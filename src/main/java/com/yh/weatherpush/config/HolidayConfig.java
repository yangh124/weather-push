package com.yh.weatherpush.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : yh
 * @date : 2021/11/21 14:29
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "holiday")
public class HolidayConfig {

    private String todayUrl;

    private String dateUrl;
}
