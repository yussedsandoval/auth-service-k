package com.logitrack.auth.controller;

import com.logitrack.auth.dto.LoginRequest;
import com.logitrack.auth.dto.TokenResponse;
import com.logitrack.auth.dto.UserRegisterDTO;
import com.logitrack.auth.dto.UserRegisterResponse;
import com.logitrack.auth.service.UserService;
import com.logitrack.auth.util.Role;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> createUser(@RequestBody @Valid UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto, Role.CUSTOMER));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest dto) {
        return ResponseEntity.ok(userService.createToken(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserRegisterResponse> me(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(userService.login(userId));
    }

}
