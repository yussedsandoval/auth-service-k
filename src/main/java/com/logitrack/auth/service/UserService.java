package com.logitrack.auth.service;

import com.logitrack.auth.dto.LoginRequest;
import com.logitrack.auth.dto.TokenResponse;
import com.logitrack.auth.exception.EmailAlreadyExistsException;
import com.logitrack.auth.dto.UserRegisterDTO;
import com.logitrack.auth.dto.UserRegisterResponse;
import com.logitrack.auth.entity.User;
import com.logitrack.auth.exception.InvalidCredentialsException;
import com.logitrack.auth.repository.IUserRepository;
import com.logitrack.auth.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public UserRegisterResponse createUser(UserRegisterDTO dto, Role role) {
        String email = dto.email().toLowerCase(Locale.ROOT).trim();
        boolean exists = userRepository.existsByEmail(email);
        if (exists) {
            throw new EmailAlreadyExistsException(email);
        }

        User user = userRepository.save(
                new User(
                        email,
                        passwordEncoder.encode(dto.password()),
                        dto.fullName(),
                        role
                )
        );

        return UserRegisterResponse.from(user);
    }

    public TokenResponse createToken(LoginRequest loginRequest) {
        String email = loginRequest.email().toLowerCase(Locale.ROOT).trim();
        User user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(loginRequest.password(), user.getPasswordHashed())) {
            throw new InvalidCredentialsException();
        }
        return tokenService.generateToken(user);
    }

    public UserRegisterResponse login(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(InvalidCredentialsException::new);
        return UserRegisterResponse.from(user);
    }
}
