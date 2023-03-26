package com.yh.weatherpush.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
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
public class Swagger2Config {

    @Value("${swagger.enable:false}")
    private boolean enabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(enabled).select()
                // 为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.yh.weatherpush.controller"))
                // 为有@Api注解的Controller生成API文档
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 为有@ApiOperation注解的方法生成API文档
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("yanghao", "https://github.com/yangh124", "yh.124@qq.com");
        return new ApiInfoBuilder().title("天气推送").description("天气推送").contact(contact).version("1.0").build();
    }
}