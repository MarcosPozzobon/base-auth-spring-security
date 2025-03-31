package com.marcos.desenvolvimento.controllers;

import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.security.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.status(200).body(loginService.validateLogin(loginRequest));
    }
}
