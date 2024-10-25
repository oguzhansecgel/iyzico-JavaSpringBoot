package com.os.customer_service.controller;

import com.os.customer_service.dto.request.user.LoginRequest;
import com.os.customer_service.dto.request.user.RegisterRequest;
import com.os.customer_service.dto.response.user.LoginResponse;
import com.os.customer_service.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public void register(@Valid @RequestBody RegisterRequest request)
    {
        authService.register(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpSession session)
    {
        return authService.login(request,session);
    }

}