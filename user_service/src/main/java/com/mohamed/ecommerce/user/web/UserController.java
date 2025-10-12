package com.mohamed.ecommerce.user.web;

import com.mohamed.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping
    // POST /api/users gives 201 Created with Location + body
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse created = service.register(request);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(created);
    }

    // GET /api/users/{id} gives 200 OK or 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
