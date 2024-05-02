package com.yh.weatherpush.infrastructure.config;

import com.alibaba.fastjson2.support.spring6.http.codec.Fastjson2Decoder;
import com.alibaba.fastjson2.support.spring6.http.codec.Fastjson2Encoder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yh.weatherpush.infrastructure.manager.http.HfGeoApi;
import com.yh.weatherpush.infrastructure.manager.http.HfWeatherApi;
import com.yh.weatherpush.infrastructure.manager.http.HolidayApi;
import com.yh.weatherpush.infrastructure.manager.http.QywxApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Value("${holiday.url}")
    private String holidayUrl;
    @Value("${hf-weather.city-url}")
    private String cityUrl;
    @Value("${hf-weather.base-url}")
    private String weatherUrl;
    @Value("${qywx.base-url}")
    private String qywxUrl;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .registerModule(new JavaTimeModule());
    }

    @Bean
    public CodecCustomizer codecCustomizer(ObjectMapper customObjectMapper) {
        return configurer -> {
            MimeType mimeType = MimeType.valueOf(MediaType.APPLICATION_JSON_VALUE);
            CodecConfigurer.CustomCodecs customCodecs = configurer.customCodecs();
            customCodecs.register(new Fastjson2Decoder(customObjectMapper, mimeType));
            customCodecs.register(new Fastjson2Encoder(customObjectMapper, mimeType));
        };
    }


    @Bean
    public HolidayApi holidayApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(holidayUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(60)).build().createClient(HolidayApi.class);
    }

    @Bean
    public HfGeoApi hfGeoApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(cityUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(60)).build().createClient(HfGeoApi.class);
    }

    @Bean
    public HfWeatherApi hfWeatherApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(weatherUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(60)).build().createClient(HfWeatherApi.class);
    }

    @Bean
    public QywxApi qywxApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(qywxUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(60)).build().createClient(QywxApi.class);
    }
}
