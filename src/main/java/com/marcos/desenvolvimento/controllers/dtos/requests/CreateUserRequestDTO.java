package com.marcos.desenvolvimento.controllers.dtos.requests;

public record CreateUserRequestDTO(String name, String email, String password, String role, Boolean active) {
}
