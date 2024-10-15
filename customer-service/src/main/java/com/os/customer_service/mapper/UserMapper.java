package com.os.customer_service.mapper;

import com.os.customer_service.dto.request.user.RegisterRequest;
import com.os.customer_service.dto.request.user.UpdateUserRequest;
import com.os.customer_service.dto.response.user.GetByIdUserResponse;
import com.os.customer_service.dto.response.user.UserResponse;
import com.os.customer_service.model.Role;
import com.os.customer_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userFromRequest(RegisterRequest request);

    User updateUser(UpdateUserRequest request,@MappingTarget User user);

    @Mapping(target = "roleId", source = "roles", qualifiedByName = "mapRolesToRoleId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    GetByIdUserResponse getByIdUserResponse(User user);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roleId", source = "roles", qualifiedByName = "mapRolesToRoleId")
    UserResponse userFromResponse(User user);
    List<UserResponse> usersFromResponse(List<User> users);

    @Named("mapRolesToRoleId")
    default int mapRolesToRoleId(Set<Role> roles) {
        return roles.isEmpty() ? 0 : roles.iterator().next().getId();
    }
}
