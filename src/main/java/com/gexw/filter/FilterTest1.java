package com.gexw.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class FilterTest1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(1111111);
        response.setContentType("text/html;charset=utf-8");
        request.setAttribute("user",1111);


        Object user = request.getAttribute("user");
        System.out.println(user);
        //放行
        filterChain.doFilter(request, response);
    }
}
