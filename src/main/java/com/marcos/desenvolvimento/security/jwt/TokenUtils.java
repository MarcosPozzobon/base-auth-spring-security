package com.marcos.desenvolvimento.security.jwt;

import com.marcos.desenvolvimento.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtils {

    @Value("${api.app.jwtSecret}")
    private String jwtSecret;

    @Value("${api.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Getter
    private static ExpiredJwtException lastExpiredException;
    public String getTokenFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

    public String getUserName(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey(jwtSecret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSigningKey(String string) {
        return Keys.hmacShaKeyFor(string.getBytes(StandardCharsets.UTF_8));
    }

    public String generateTokenFromUserDetails(UserDetailsImpl user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .subject(user.getUsername())
                .claim("issuer", "clean-backend-api")
                .claim("name", user.getUsername())
                .claim("authorities", user.getAuthorities())
                .issuedAt(new Date())
                //.expiration(Date.from(LocalDateTime.now().plusMinutes(1)
                //        .atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSigningKey(jwtSecret))
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(getSigningKey(jwtSecret)).build().parseSignedClaims(token);
            return true;
        } catch (WeakKeyException | SecurityException e) {
            //throw e;
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            //throw e;
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            lastExpiredException = e;
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            //throw e;
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
