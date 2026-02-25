package com.example.lab8.config;

import com.example.lab8.interceptor.AuthInterceptor;
import com.example.lab8.interceptor.LogInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Autowired
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LogInterceptor áp dụng cho tất cả các đường dẫn (hoặc chỉ đường dẫn bảo mật)
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/order/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**", "/account/change-password", "/account/edit-profile", "/order/**")
                .excludePathPatterns("/admin/home/index");
    }
}