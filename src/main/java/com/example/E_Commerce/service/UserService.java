package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.user.UpdateProfileRequest;
import com.example.E_Commerce.model.User;

public interface UserService {

    User getCurrentUser();

    User getUserById(Long id);

    User updateProfile(UpdateProfileRequest request);
}