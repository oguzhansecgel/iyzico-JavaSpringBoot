package com.os.customer_service.service.impl;

import com.os.customer_service.dto.request.user.LoginRequest;
import com.os.customer_service.dto.request.user.RegisterRequest;
import com.os.customer_service.dto.response.user.LoginResponse;
import com.os.customer_service.exception.exceptionMessage.UserMessage;
import com.os.customer_service.exception.type.PasswordNotMatchException;
import com.os.customer_service.exception.type.WrongUsernameOrPassword;
import com.os.customer_service.mapper.UserMapper;
import com.os.customer_service.model.User;
import com.os.customer_service.service.AuthService;
import com.os.customer_service.service.UserService;
import com.turkcell.tcell.core.security.BaseJwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BaseJwtService baseJwtService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserService userService, AuthenticationManager authenticationManager, BaseJwtService baseJwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.baseJwtService = baseJwtService;
    }

    @Override
    public void register(RegisterRequest request) {
        if(request.getPassword().equals(request.getPasswordRepeat()))
        {
            User user = UserMapper.INSTANCE.userFromRequest(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.add(user);
        }
        else
            throw new PasswordNotMatchException(UserMessage.PASSWORD_NOT_MATCH);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (!authentication.isAuthenticated())
            {
                throw new WrongUsernameOrPassword(UserMessage.WRONG_USER_NAME_PASSWORD);
            }
            UserDetails user = userService.loadUserByUsername(loginRequest.getEmail());
            Long userId = ((User) user).getId();
            Map<String, Object> claims = new HashMap<>();
            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(role -> role.getAuthority())
                    .toList();
            claims.put("roles", roles);
            claims.put("userId", userId);
            String token = baseJwtService.generateToken(loginRequest.getEmail(), claims);

            return new LoginResponse(userId,token);
        } catch (BadCredentialsException e) {
            throw new WrongUsernameOrPassword(UserMessage.WRONG_USER_NAME_PASSWORD);
        }
    }
}