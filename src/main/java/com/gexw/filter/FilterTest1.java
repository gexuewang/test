package com.gexw.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class FilterTest1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");


        Object user = request.getAttribute("user");
        //放行
        filterChain.doFilter(request, response);
    }
}
