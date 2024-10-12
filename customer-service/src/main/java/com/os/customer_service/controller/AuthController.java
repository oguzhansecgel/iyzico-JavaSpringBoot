package com.os.customer_service.controller;

import com.os.customer_service.dto.request.user.LoginRequest;
import com.os.customer_service.dto.request.user.RegisterRequest;
import com.os.customer_service.dto.response.user.LoginResponse;
import com.os.customer_service.service.AuthService;
import com.os.customer_service.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;
    private final RoleService roleService;

    public AuthController(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }

    @PostMapping("register")
    public void register(@Valid @RequestBody RegisterRequest request)
    {
        authService.register(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request)
    {
        return authService.login(request);
    }
}