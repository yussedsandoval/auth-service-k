package com.logitrack.auth.dto;

import com.logitrack.auth.entity.User;
import com.logitrack.auth.util.Role;

import java.util.UUID;

public record UserRegisterResponse(
        UUID id,
        String email,
        String fullName,
        Role role) {

    public static UserRegisterResponse from(User user) {
        return new UserRegisterResponse(user.getId(), user.getEmail(), user.getFullName(), user.getRole());
    }
}
