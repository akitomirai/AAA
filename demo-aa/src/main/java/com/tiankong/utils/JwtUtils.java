package com.tiankong.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.tiankong.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    // 密钥（生产环境应配置在外部）
    private static final String SECRET = "douba_edu_secret_key_2025";
    // 令牌有效期（3600秒=1小时）
    private static final long EXPIRE = 3600 * 1000L;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserAccount())
                .claim("userId", user.getId())
                .claim("userRole", user.getUserRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
