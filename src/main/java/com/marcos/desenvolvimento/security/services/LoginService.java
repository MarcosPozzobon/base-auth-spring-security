package com.marcos.desenvolvimento.security.services;

import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.security.jwt.TokenUtils;
import com.marcos.desenvolvimento.usecases.FindUser;
import com.marcos.desenvolvimento.usecases.VerifyCredentials;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final FindUser findUser;

    private final VerifyCredentials verifyCredentials;

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    public HashMap<String, Object> validateLogin(final LoginRequestDTO loginRequest) {

        HashMap<String, Object> validToken = new HashMap<>();

        findUser.byLogin(loginRequest.login());

        var encodedPassword = verifyCredentials.getEncodedPasswordByLogin(loginRequest.login());

        if(loginRequest.password() == null || loginRequest.password().isEmpty()){
            throw new InvalidCredentialsException("The password cannot be null or empty.");
        }

        if(!verifyCredentials.passwordMatches(loginRequest.password(), encodedPassword)){
            throw new InvalidCredentialsException();
        };

        final var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

        var userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var token = tokenUtils.generateTokenFromUserDetails(userDetails);
        validToken.put("accessToken", token);
        return validToken;
    }
}
