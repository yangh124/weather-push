package com.yh.weatherpush.config.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class SaTokenWebMvcConfig implements WebMvcConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "white-list")
    public List<String> whiteList() {
        return new ArrayList<>();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeUrlList =
                CollUtil.newArrayList("/**/*.ico", "/**/*.png", "/**/*.html", "/**/*.css", "/**/*.js",
                        "/swagger-resources/**", "/v3/api-docs/**");
        List<String> whiteList = whiteList();
        if (CollUtil.isNotEmpty(whiteList)) {
            excludeUrlList.addAll(whiteList);
        }
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> {
                    try {
                        log.debug("-------- 前端访问path：" + SaHolder.getRequest().getRequestPath());
                        StpUtil.checkLogin();
                        log.debug("-------- 此 path 校验成功：" + SaHolder.getRequest().getRequestPath());
                    } catch (Exception e) {
                        log.debug("-------- 此 path 校验失败：" + SaHolder.getRequest().getRequestPath());
                        throw e;
                    }
                }))
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrlList);
    }

    // 资源映射增加
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    // <-cors
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }
    // cors->


    // <-Validator
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败(Fail Fast)模式，设置为true即可，如果想验证全部，则设置为false或者取消配置即可
                .failFast(true)
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    /**
     * requestParam方式的校验
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }


    /**
     * 让spring的请求校验Validator使用我们上边的validator，让设置的failFast生效
     *
     * @return
     */
    @Override
    public org.springframework.validation.Validator getValidator() {
        return new SpringValidatorAdapter(validator());
    }
}
