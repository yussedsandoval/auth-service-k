package com.logitrack.auth.dto;

import com.logitrack.auth.util.Role;

public record UserRegisterResponse(
        String email,
        String fullName,
        Role role) {
}
