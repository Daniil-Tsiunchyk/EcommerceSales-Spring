package com.example.MonitoringInternetShop.Secure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminCheckInterceptor()).addPathPatterns("/dashboard", "/edit_order", "/editUser", "/orders", "/products", "/promotions", "/settings", "/userManagement");
        registry.addInterceptor(new UserCheckInterceptor()).addPathPatterns("/create-order", "/user-orders");
    }

}