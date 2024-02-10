package com.yh.weatherpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author yh
 */
@Import(cn.hutool.extra.spring.SpringUtil.class)
@SpringBootApplication
public class WeatherPushApplication {

    /**
     * 使用了jasypt加密yaml文件中的敏感信息
     * 启动时需要添加参数 -Djasypt.encryptor.password=xxxx（你设置的加密口令）
     */
    public static void main(String[] args) {
        SpringApplication.run(WeatherPushApplication.class, args);
    }

}
