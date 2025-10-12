package com.mohamed.ecommerce.user.service;

import com.mohamed.ecommerce.user.domain.User;
import com.mohamed.ecommerce.user.repo.UserRepository;
import com.mohamed.ecommerce.user.web.RegisterUserRequest;
import com.mohamed.ecommerce.user.web.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager em;

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

        User saved = repo.saveAndFlush(user);
        em.refresh(saved);
        saved = repo.findById(saved.getId()).orElseThrow();

        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getCreatedAt()
        );
    }
    @Transactional(readOnly = true)
    public Optional<UserResponse> getById(UUID id) {
        return repo.findById(id).map(this::toResponse);
    }
    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getFullName(), u.getCreatedAt());
    }
}