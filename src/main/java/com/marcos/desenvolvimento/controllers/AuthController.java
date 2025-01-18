package com.marcos.desenvolvimento.controllers;

import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.security.services.LoginService;
import com.marcos.desenvolvimento.usecases.FindUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUser findUser;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        final var jwtToken = loginService.validateLogin(loginRequest);
        HashMap<String, String> response = new HashMap<>();
        response.put("accessToken", jwtToken);
        return ResponseEntity.status(200).body(response);
    }
}
