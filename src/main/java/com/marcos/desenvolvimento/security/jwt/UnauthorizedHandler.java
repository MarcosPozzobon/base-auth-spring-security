package com.marcos.desenvolvimento.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("path", request.getServletPath());

        try {
            Field field = TokenUtils.class.getDeclaredField("lastExpiredException");
            field.setAccessible(true);
            ExpiredJwtException expiredJwtException = (ExpiredJwtException) field.get(null);

            if (expiredJwtException != null) {
                body.put("error", "Expired token.");
                body.put("message", "Your token has expired. Log in again.");
                field.set(null, null); // resetando a exception apos a validacao p/ n impactar reqs futuras
            } else {
                body.put("error", "Unauthorized");
                body.put("message", authException.getMessage());
            }
        } catch (Exception e) {
            log.error("Error while accessing the exception through reflection.", e);
            body.put("error", "Unauthorized");
            body.put("message", "Unknown error while authenticating.");
        }

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
