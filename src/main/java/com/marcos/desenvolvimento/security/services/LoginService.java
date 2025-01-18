package com.marcos.desenvolvimento.security.services;

import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.security.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public String validateLogin(final LoginRequestDTO loginRequest) {
        final var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

        var userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return tokenUtils.generateTokenFromUserDetails(userDetails);
    }
}
