package com.gexw.config;


import com.gexw.filter.FilterTest1;
import com.gexw.filter.FilterTest2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class filterConfig {

    @Autowired
    private FilterTest1 filterTest1;

    @Autowired
    private FilterTest2 filterTest2;
    @Bean
    public FilterRegistrationBean Test1filterRegistrationBean() {
        FilterRegistrationBean Test1filterRegistrationBean = new FilterRegistrationBean();
        Test1filterRegistrationBean.setFilter(filterTest1);
        Test1filterRegistrationBean.setOrder(3);
        Test1filterRegistrationBean.addUrlPatterns("/*");
        return Test1filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean Test2filterRegistrationBean() {
        FilterRegistrationBean Test2filterRegistrationBean = new FilterRegistrationBean();
        Test2filterRegistrationBean.setFilter(filterTest2);
        Test2filterRegistrationBean.setOrder(1);
        Test2filterRegistrationBean.addUrlPatterns("/*");
        return Test2filterRegistrationBean;
    }

}
