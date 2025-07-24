package com.gexw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
        .allowedOrigins("*")   // 允许跨域访问的源
//.allowedOrigins("http://localhost")   // 允许跨域访问的源

        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") // 允许请求方法
        .maxAge(168000)    // 预检间隔时间
        .allowedHeaders("*")  // 允许头部设置
        .allowCredentials(true);   // 是否发送cookie
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射，将/uploads/**映射到临时目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("java.io.tmpdir") + "/");

        // 配置upload目录的静态资源映射，指向C:/images/
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:C:/images/");
    }
}