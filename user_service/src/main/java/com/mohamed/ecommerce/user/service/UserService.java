package com.mohamed.ecommerce.user.service;

import com.mohamed.ecommerce.user.domain.User;
import com.mohamed.ecommerce.user.repo.UserRepository;
import com.mohamed.ecommerce.user.web.RegisterUserRequest;
import com.mohamed.ecommerce.user.web.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest req) {
        repo.findByEmail(req.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already in use");
        });
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        User saved = repo.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getCreatedAt()
        );
    }
}