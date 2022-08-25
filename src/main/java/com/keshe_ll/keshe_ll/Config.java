package com.keshe_ll.keshe_ll;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/teacherLogin").setViewName("teacherLogin");
        registry.addViewController("/teacherIndex").setViewName("teacherIndex");
        registry.addViewController("/studentIndex").setViewName("studentIndex");
        registry.addViewController("/courseManage").setViewName("courseManage");
        registry.addViewController("/changePassword").setViewName("changePassword");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new Handler());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/index",
                "/teacherLogin",
                "/register",
                "/teacherloginRequest",
                "/studentlogin",
                "/registerrequest",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        );
    }
}
