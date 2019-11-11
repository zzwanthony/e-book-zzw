package com.zzw.common.config;

import com.zzw.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自己的拦截器,并设置拦截路径，拦截多个可以全一个list集合
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/administrator/**").addPathPatterns("/readerUser/**");
    }
}