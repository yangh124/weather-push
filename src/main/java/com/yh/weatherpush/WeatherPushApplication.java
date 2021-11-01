package com.yh.weatherpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yh
 */
@EnableScheduling
@SpringBootApplication
public class WeatherPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherPushApplication.class, args);
    }

}
