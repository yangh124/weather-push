package com.yh.weatherpush.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/10/31 13:16
 */
@Configuration
public class HttpMessageConvertersConfig {

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.PrettyFormat,
                JSONWriter.Feature.WriteNullStringAsEmpty,
                JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteMapNullValue);
        List<MediaType> mediaTypes = new ArrayList<>(5);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
