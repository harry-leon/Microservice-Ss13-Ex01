package com.rikkei.microservice.identity.service;

import com.rikkei.microservice.identity.dto.RegisterRequest;
import com.rikkei.microservice.identity.dto.UserResponse;
import com.rikkei.microservice.identity.entity.User;
import com.rikkei.microservice.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        String role = (request.getRole() == null || request.getRole().trim().isEmpty())
                ? "ROLE_USER"
                : request.getRole();

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }
}
