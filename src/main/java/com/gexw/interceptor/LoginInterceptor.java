package com.gexw.interceptor;



import com.gexw.util.JwtHelper;
import com.gexw.util.ThreadlocalUserId;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置CORS头部
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, token");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        // 处理OPTIONS预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        
        try {
            // 从请求头中获取token
          //  String token = request.getHeader("Authorization");
            String token = request.getHeader("access_token");
            
            // 如果请求头中没有token，尝试从请求参数中获取
            if (!StringUtils.hasText(token)) {
             //   token = request.getParameter("Authorization");
                token = request.getParameter("access_token");
            }

            // 检查token是否为空
            if (!StringUtils.hasText(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未提供有效的认证令牌\"}");
                return false;
            }

            // 验证token是否过期
            if (JwtHelper.isTokenExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"认证令牌已过期，请重新登录\"}");
                return false;
            }

            // 从token中获取用户ID
            Integer userId = JwtHelper.getUserId(token);

            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"认证令牌无效\"}");
                return false;
            }

            // 将用户ID存储到ThreadLocal中，供后续业务逻辑使用
            ThreadlocalUserId.set(userId.longValue());
            
            return true;
            
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"认证失败，请重新登录\"}");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //释放资源
        ThreadlocalUserId.remove();
    }
}
