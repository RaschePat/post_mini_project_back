package com.korit.post_mini_project_back.jwt;

import com.korit.post_mini_project_back.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;

    // 생성자: application.yml의 jwt.secret 값을 주입받음
    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        // 비밀키를 바이트 배열로 변환 → SecretKey 객체 생성
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰 생성 메서드
    public String createAccessToken(User userEntity) {
        Date now = new Date();      // 현재 시간
        long expiredTime = now.getTime() + (1000l * 60l * 60l * 24l);   // 24시간 후
        Date expiredDate = new Date(expiredTime);       // 만료 시간

        return Jwts.builder()
                .subject("Server Access Token")          // 토큰 제목
                .issuer("rasche")                      // 발행자
                .issuedAt(new Date())                    // 발행 시간
                .expiration(expiredDate)                 // 만료 시간 (필수!)
                .claim("userId", userEntity.getUserId()) // 사용자 ID 저장 (필수!)
                .signWith(key, SignatureAlgorithm.HS256) // 서명 (필수!)
                .compact();                              // 문자열로 변환
    }

    public boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(key)
                    .build();
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public int getUserId(String token) {
        return (int) Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("userId");
    }
}