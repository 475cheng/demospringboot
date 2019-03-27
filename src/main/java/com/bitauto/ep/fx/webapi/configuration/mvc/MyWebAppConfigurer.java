package com.bitauto.ep.fx.webapi.configuration.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Spring Mvc 配置管理
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{


    @Bean
    public GhInterceptor getGhInterceptor() {
        return new GhInterceptor();
    }

    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getGhInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 注册指定路由映射到视图，不需要写对应的Controller和Action
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/toupload").setViewName("index");
        super.addViewControllers(registry);
    }

    /**
     * 配置路由匹配，修正字符分割符(".")
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }
}
