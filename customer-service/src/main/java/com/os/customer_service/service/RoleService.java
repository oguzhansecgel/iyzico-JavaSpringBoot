package com.os.customer_service.service;

import com.os.customer_service.dto.request.role.RoleRequest;
import com.os.customer_service.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role addRole(RoleRequest request);

    void deleteRole(Long id);

    Optional<Role> getRoleById(Long id);

    List<Role> getAllRoles();
}