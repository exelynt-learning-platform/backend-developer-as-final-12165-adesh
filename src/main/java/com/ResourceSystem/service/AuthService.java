package com.ResourceSystem.service;

import com.ResourceSystem.dto.LoginRequest;
import com.ResourceSystem.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
