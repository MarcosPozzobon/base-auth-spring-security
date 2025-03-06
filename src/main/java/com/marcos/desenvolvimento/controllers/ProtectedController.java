package com.marcos.desenvolvimento.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/protected")
public class ProtectedController {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String displayOkMessage(){
        return "If you are seeing this, you are fully authenticated.";
    }

}
