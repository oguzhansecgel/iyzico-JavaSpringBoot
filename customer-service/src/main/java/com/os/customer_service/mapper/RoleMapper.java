package com.os.customer_service.mapper;

import com.os.customer_service.dto.request.role.RoleRequest;
import com.os.customer_service.dto.response.role.RoleWithUserResponse;
import com.os.customer_service.model.Role;
import com.os.customer_service.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role roleFromRequest(RoleRequest request);

    @Mapping(source = "roleId", target = "id")
    Role toRole(RoleWithUserResponse roleWithUserResponse);

    @Mapping(source = "userId", target = "id")
    User toUser(RoleWithUserResponse roleWithUserResponse);
}
