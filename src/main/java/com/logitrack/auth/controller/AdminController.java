package com.logitrack.auth.controller;

import com.logitrack.auth.dto.UserRegisterDTO;
import com.logitrack.auth.dto.UserRegisterResponse;
import com.logitrack.auth.service.UserService;
import com.logitrack.auth.util.Role;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/courier")
    public ResponseEntity<UserRegisterResponse> createUserCourier(@RequestBody @Valid UserRegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto, Role.COURIER));
    }
}
