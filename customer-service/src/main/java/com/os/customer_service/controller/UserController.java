package com.os.customer_service.controller;

import com.os.customer_service.dto.request.user.UpdateUserRequest;
import com.os.customer_service.dto.response.user.GetByIdUserResponse;
import com.os.customer_service.dto.response.user.UpdateUserResponse;
import com.os.customer_service.dto.response.user.UserResponse;
import com.os.customer_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getByIdUser/{id}")
    public Optional<GetByIdUserResponse> getByIdUser(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }
    @GetMapping("/getAllUsers")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUser();
    }
    @PutMapping("/update/users/{userId}")
    public UpdateUserResponse updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest request)
    {
        return userService.updateUser(request,userId);
    }
    @DeleteMapping("/delete/account/{id}")
    public void deleteAccount(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }
}