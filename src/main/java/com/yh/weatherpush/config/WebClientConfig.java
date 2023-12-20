package com.yh.weatherpush.config;

import com.yh.weatherpush.manager.http.HolidayApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Value("${holiday.url}")
    private String holidayUrl;

    @Bean
    public HolidayApi holidayApi(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.baseUrl(holidayUrl).build();
        return HttpServiceProxyFactory.builder().clientAdapter(WebClientAdapter.forClient(webClient))
                .build().createClient(HolidayApi.class);
    }
}
