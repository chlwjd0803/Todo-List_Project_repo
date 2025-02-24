package com.example.todo_list;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;
import com.example.todo_list.entity.WebUser;

@Component
public class JwtUtil {
    // 서명에 사용할 비밀 키 (노출되지 않도록 주의)
    private final String secretKey = "your-very-secure-secret-key";

    // 토큰 만료 시간 (예: 24시간)
    private final long expirationMs = 86400000;

    // 토큰 생성 메서드
    public String generateToken(WebUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(user.getUsername()) // 토큰 주체를 유저이름으로 설정
                .claim("userId", user.getId())    // 필요한 추가 정보 (예: userId) 추가
                .setIssuedAt(now)                 // 토큰 발급 시간
                .setExpiration(expiryDate)        // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS512, secretKey) // 서명 및 암호화
                .compact();
    }

    // 토큰으로부터 정보 추출 (Claims 추출)
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 검증 메서드
    public boolean validateToken(String token, WebUser user) {
        Claims claims = extractClaims(token);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();

        // 토큰의 주체와 유저 정보 일치 여부와 만료시간 검증
        return (username.equals(user.getUsername()) && !expiration.before(new Date()));
    }

    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }
}
