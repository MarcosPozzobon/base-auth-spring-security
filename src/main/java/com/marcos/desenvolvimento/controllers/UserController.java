package com.marcos.desenvolvimento.controllers;

import com.marcos.desenvolvimento.controllers.dtos.requests.CreateUserRequestDTO;
import com.marcos.desenvolvimento.usecases.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping("/create")
    public ResponseEntity<Void> createNewUser(@RequestBody final CreateUserRequestDTO createUserRequestDTO){
        userUseCase.createUser(createUserRequestDTO);
        return ResponseEntity.status(201).build();
    }

}
