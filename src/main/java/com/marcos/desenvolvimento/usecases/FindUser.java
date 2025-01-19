package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.exceptions.UserNotFoundException;
import com.marcos.desenvolvimento.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public record FindUser(UserRepository userRepository) {

    public User byLogin(String login){
        if(login == null || login.isEmpty()){
            throw new InvalidCredentialsException();
        }

        if(!existsByLogin(login)){
            throw new UserNotFoundException();
        }
        return userRepository.findByLogin(login);

    }
    public Boolean existsByLogin(final String login) {
        return userRepository.existsByLogin(login);
    }

}
