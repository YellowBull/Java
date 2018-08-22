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
        registry.addViewController("/index").setViewName("security/index");
        registry.addViewController("/").setViewName("security/login");
        registry.addViewController("/logout").setViewName("security/login");
        registry.addViewController("/login").setViewName("security/login");
        registry.addViewController("/super").setViewName("security/super");
        registry.addViewController("/admin").setViewName("security/admin");
        registry.addViewController("/vip").setViewName("security/vip");
        registry.addViewController("/user").setViewName("security/user");
    }
}
