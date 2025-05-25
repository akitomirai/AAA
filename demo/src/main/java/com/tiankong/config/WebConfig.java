package com.tiankong.config;

import com.tiankong.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")     //拦截所以请求
//                .excludePathPatterns(
//                        "/user/login",
//                        "/error",
//                        "/static/**",
//                        "/public/**");   //白名单
    }

    /**
     * 配置全局的跨域资源共享(CORS)策略，允许前端应用进行跨域请求。
     * 此方法设置了宽松的CORS策略，适用于开发环境，生产环境应根据需求调整以增强安全性。
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有API路径
                .allowCredentials(true) // 允许携带凭证（如Cookies、认证信息），需注意配合具体来源使用
                .allowedOriginPatterns("*") // 允许所有来源（生产环境建议替换为具体域名，例如"https://example.com"）
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"}) // 允许的HTTP方法
                .allowedHeaders("*") // 允许所有请求头（可根据需求限制为必要头部，如"Authorization","Content-Type"）
                .exposedHeaders("*"); // 暴露所有响应头，使客户端能读取自定义头部
    }
}
