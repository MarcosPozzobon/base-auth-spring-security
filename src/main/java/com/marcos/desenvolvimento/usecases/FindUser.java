package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public record FindUser(UserRepository userRepository) {

    public Optional<User> byId(final Long id) {
        return userRepository.findById(id);
    }

    public User byLogin(String login){
        if(login == null || login.isEmpty()){
            throw new InvalidCredentialsException();
        }
        return userRepository.findByLogin(login);
    }

//    public User byLoginOrEmail(String loginOrEmail) {
//        boolean isEmail = isEmail(loginOrEmail);
//
//        if (isEmail) {
//            return userGateway.findByEmail(loginOrEmail);
//        } else {
//            return userGateway.findByLogin(loginOrEmail);
//        }
//    }

    public Boolean existsByLogin(final String login) {
        return userRepository.existsByLogin(login);
    }

//    private Boolean isEmail(String username) {
//        if (username == null) {
//            return false;
//        }
//        return EMAIL_PATTERN.matcher(username).matches();
//    }
}
