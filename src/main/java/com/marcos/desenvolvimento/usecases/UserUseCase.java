package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.controllers.dtos.requests.CreateUserRequestDTO;
import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.exceptions.InvalidObjectException;
import com.marcos.desenvolvimento.exceptions.UserNotFoundException;
import com.marcos.desenvolvimento.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
public record UserUseCase(UserRepository userRepository) {

    public void createUser(final CreateUserRequestDTO createUserRequestDTO){

        if(createUserRequestDTO.email() == null || createUserRequestDTO.email().isEmpty()){
            throw new InvalidObjectException("Email cannot be null or empty!");
        }
        if(createUserRequestDTO.name() == null || createUserRequestDTO.name().isEmpty()){
            throw new InvalidObjectException("Email cannot be null or empty!");
        }

        userRepository.save(
                    User
                        .builder()
                        .active(Boolean.TRUE)
                        .admin(Boolean.FALSE)
                        .createdAt(now())
                        .email(createUserRequestDTO.email())
                        .lastAccess(null)
                        .login(createUserRequestDTO.email())
                        .name(createUserRequestDTO.name())
                        .password(new BCryptPasswordEncoder().encode(createUserRequestDTO.password()))
                        .build()
                    );
    }


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
