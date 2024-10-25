package com.os.customer_service.service;

import com.os.customer_service.dto.request.user.LoginRequest;
import com.os.customer_service.dto.request.user.RegisterRequest;
import com.os.customer_service.dto.response.user.LoginResponse;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    void register(RegisterRequest request);

    LoginResponse login(LoginRequest loginRequest, HttpSession session);
}