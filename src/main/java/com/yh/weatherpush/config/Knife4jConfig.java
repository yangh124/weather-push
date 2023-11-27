package com.yh.weatherpush.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 *
 * @author yh
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Value("${swagger.enable:false}")
    private boolean enable;

    /**
     * 接口地址：<a href="http://127.0.0.1:8080/doc.html">...</a>
     */
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Contact contact = new Contact("yh124", "https://github.com/yangh124", "yh.124@qq.com");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("# weather-push RESTful APIs")
                        .termsOfServiceUrl("https://yh124.space/")
                        .contact(contact)
                        .version("1.0")
                        .build())
                .enable(enable)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.yh.weatherpush.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}