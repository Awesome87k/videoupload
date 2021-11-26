package com.uploadservice.config.security;

import com.uploadservice.video.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtComponent {
    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String _token) {
        return extractAllClaims(_token).get("username", String.class);
    }

    public Boolean isTokenExpired(String _token) {
        final Date expiration = extractAllClaims(_token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(String _email) {
        return doGenerateToken(_email, TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(UserEntity _user) {
        return doGenerateToken(_user.getVu_email(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(String _username, long _expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", _username);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + _expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String _token, UserDetails _userDetails) {
        final String username = getUsername(_token);
        return (username.equals(_userDetails.getUsername()) && !isTokenExpired(_token));
    }
}