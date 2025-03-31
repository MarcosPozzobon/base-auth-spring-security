package com.marcos.desenvolvimento.controllers;

import com.marcos.desenvolvimento.controllers.dtos.responses.TestUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/protected")
@CrossOrigin(origins = "http://localhost:5173")
public class ProtectedController {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String displayOkMessage(){
        return "If you are seeing this, you are fully authenticated as admin.";
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('TEST')")
    public String displayOkMessageWhenTest(){
        return "If you are seeing this, you are fully authenticated as test.";
    }

    @GetMapping("/users")
    public ResponseEntity<List<TestUserDTO>> listUsers(){
        TestUserDTO testUserDTO = new TestUserDTO("Marcos", "marcos@email.com", "ADMIN", "active");
        TestUserDTO testUserDTO1 = new TestUserDTO("Isis", "isis@email.com", "ADMIN", "active");
        TestUserDTO testUserDTO2= new TestUserDTO("Otavio", "otavio@email.com", "ADMIN", "active");

        return ResponseEntity.status(200).body(List.of(testUserDTO, testUserDTO1, testUserDTO2));
    }

}
