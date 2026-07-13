package com.example.E_Commerce.controller;

import com.example.E_Commerce.dto.user.UpdateProfileRequest;
import com.example.E_Commerce.model.User;
import com.example.E_Commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/profile")
    public User updateProfile(
            @RequestBody UpdateProfileRequest request
    ) {
        return userService.updateProfile(request);
    }
}