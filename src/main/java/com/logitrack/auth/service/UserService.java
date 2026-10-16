package com.logitrack.auth.service;

import com.logitrack.auth.exception.EmailAlreadyExistsException;
import com.logitrack.auth.dto.UserRegisterDTO;
import com.logitrack.auth.dto.UserRegisterResponse;
import com.logitrack.auth.entity.User;
import com.logitrack.auth.repository.IUserRepository;
import com.logitrack.auth.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponse createUser(UserRegisterDTO dto) {
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
                        Role.CUSTOMER
                )
        );

        return UserRegisterResponse.from(user);
    }
}
