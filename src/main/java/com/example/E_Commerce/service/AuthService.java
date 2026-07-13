package com.example.E_Commerce.service;

import com.example.E_Commerce.dto.auth.LoginRequest;
import com.example.E_Commerce.dto.auth.LoginResponse;
import com.example.E_Commerce.dto.auth.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}