package com.os.customer_service.service;

import com.os.customer_service.dto.request.user.UpdateUserRequest;
import com.os.customer_service.dto.response.user.GetByIdUserResponse;
import com.os.customer_service.dto.response.user.UpdateUserResponse;
import com.os.customer_service.dto.response.user.UserResponse;
import com.os.customer_service.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void add(User user);
    Optional<GetByIdUserResponse> getUserById(Long id);
    Optional<User> getUserByIdForRole(Long id);
    List<UserResponse> getAllUser();
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest,Long id);
}