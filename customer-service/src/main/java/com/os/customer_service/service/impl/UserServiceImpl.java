package com.os.customer_service.service.impl;

import com.os.customer_service.dto.request.user.UpdateUserRequest;
import com.os.customer_service.dto.response.user.GetByIdUserResponse;
import com.os.customer_service.dto.response.user.UpdateUserResponse;
import com.os.customer_service.dto.response.user.UserResponse;
import com.os.customer_service.mapper.UserMapper;
import com.os.customer_service.model.User;
import com.os.customer_service.repository.UserRepository;
import com.os.customer_service.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepositories;

    public UserServiceImpl(UserRepository userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public void add(User user) {
        userRepositories.save(user);
    }
    @Override
    public Optional<GetByIdUserResponse> getUserById(Long id) {
        Optional<User> user = userRepositories.findById(id);
        if (user.isEmpty())
        {
            throw new RuntimeException("UserMessage.USER_NOT_FOUND");
        }
        return user.map(UserMapper.INSTANCE::getByIdUserResponse);
    }

    @Override
    public Optional<User> getUserByIdForRole(Long id) {
        Optional<User> user = userRepositories.findById(id);
        return user;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepositories.findAll();
        return UserMapper.INSTANCE.usersFromResponse(users);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest, Long id) {
        Optional<User> userOptional = userRepositories.findById(id);
        if (userOptional.isEmpty()){}
        User existingUser = userOptional.get();
        User user =  UserMapper.INSTANCE.updateUser(updateUserRequest, existingUser);
        User savedUser = userRepositories.save(user);
        return new UpdateUserResponse(savedUser.getId(),savedUser.getPassword(), savedUser.getPasswordRepeat(),savedUser.getFirstName(),savedUser.getLastName());
    }

    @Override
    public void deleteUser(Long id) {
        userRepositories.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositories.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email or password "));

    }
}