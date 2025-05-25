package com.tiankong.interceptor;

import com.tiankong.utils.CommonResult;
import com.tiankong.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.PrintWriter;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public JwtInterceptor() {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从Cookie获取token
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 验证token
        if (token == null || !jwtUtils.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(
                CommonResult.error(401, "未登录或登录已过期")
            ));
            return false;
        }

        // 解析token并设置到request属性
        Claims claims = jwtUtils.parseToken(token);
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("userRole", claims.get("userRole"));
        return true;
    }
}
