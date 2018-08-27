package com.jmev.cn.service.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/super").setViewName("table");
        registry.addViewController("/admin").setViewName("table");
        registry.addViewController("/vip").setViewName("table");
        registry.addViewController("/user").setViewName("table");
    }
}
