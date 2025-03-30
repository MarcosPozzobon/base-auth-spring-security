package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public record VerifyCredentials(UserRepository userRepository) {

    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new InvalidCredentialsException("Password does not match.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String getEncodedPasswordByLogin(String login){
        return userRepository.findPasswordByLogin(login);
    }

}
