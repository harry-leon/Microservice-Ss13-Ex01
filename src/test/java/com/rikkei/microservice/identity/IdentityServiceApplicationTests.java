package com.rikkei.microservice.identity;

import com.rikkei.microservice.identity.dto.RegisterRequest;
import com.rikkei.microservice.identity.dto.UserResponse;
import com.rikkei.microservice.identity.entity.User;
import com.rikkei.microservice.identity.repository.UserRepository;
import com.rikkei.microservice.identity.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IdentityServiceApplicationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUserAndBcryptPasswordHashing() {
        RegisterRequest request = RegisterRequest.builder()
                .username("testuser")
                .password("secret123")
                .role("ROLE_USER")
                .build();

        UserResponse response = authService.register(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("ROLE_USER", response.getRole());

        User savedUser = userRepository.findById(response.getId()).orElse(null);
        assertNotNull(savedUser);
        assertNotEquals("secret123", savedUser.getPassword());
        assertTrue(passwordEncoder.matches("secret123", savedUser.getPassword()));
    }
}
