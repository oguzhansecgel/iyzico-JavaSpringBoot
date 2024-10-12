package com.os.customer_service.service.impl;

import com.os.customer_service.dto.request.role.RoleRequest;
import com.os.customer_service.mapper.RoleMapper;
import com.os.customer_service.model.Role;
import com.os.customer_service.repository.RoleRepository;
import com.os.customer_service.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepositories;

    public RoleServiceImpl(RoleRepository roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    @Override
    public Role addRole(RoleRequest request) {
        Role role = RoleMapper.INSTANCE.roleFromRequest(request);
        System.out.println("Mapped Role Name: " + role.getName());
        return roleRepositories.save(role);
    }

    @Override
    public void deleteRole(Long id) {
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepositories.findById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }
}