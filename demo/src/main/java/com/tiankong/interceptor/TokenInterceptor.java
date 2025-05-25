package com.tiankong.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求:{}", request.getRequestURI());
        // 放行预检请求（OPTIONS）
        // 1. 获取 Session（不自动创建新 Session）
        HttpSession session = request.getSession(false);
        if (session == null) {
            sendError(response, "未登录或会话已过期");
            return false;
        }

        // 2. 从 Session 中获取 Token
        String storedToken = (String) session.getAttribute("token");
        if (storedToken == null) {
            sendError(response, "Token 不存在");
            return false;
        }

        // 3. 验证 Token 有效性（此处仅为示例，可扩展）
        if (!validateToken(storedToken)) {
            sendError(response, "Token 无效或已过期");
            return false;
        }

        // 4. 刷新 Session 有效期（可选）
        session.setMaxInactiveInterval(30 * 60); // 重置为30分钟
        return true;
    }

    // Token 验证逻辑（根据需求扩展）
    private boolean validateToken(String token) {
        // 简单示例：仅检查存在性（若需增强安全，可结合数据库或 Redis）
        return true;
    }

    // 统一返回错误信息
    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\": 401, \"msg\": \"" + message + "\"}");
    }
}
