package com.yh.weatherpush;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yh
 */
@Import(cn.hutool.extra.spring.SpringUtil.class)
@EnableScheduling
@SpringBootApplication
public class WeatherPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherPushApplication.class, args);
    }

}
