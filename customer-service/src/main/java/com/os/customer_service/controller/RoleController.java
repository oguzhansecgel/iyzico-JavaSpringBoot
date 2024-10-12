package com.os.customer_service.controller;

import com.os.customer_service.dto.request.role.RoleRequest;
import com.os.customer_service.dto.response.role.RoleWithUserResponse;
import com.os.customer_service.dto.response.user.GetByIdUserResponse;
import com.os.customer_service.model.Role;
import com.os.customer_service.model.User;
import com.os.customer_service.service.RoleService;
import com.os.customer_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping("/createRoles")
    public ResponseEntity<Role> addRole(@RequestBody RoleRequest role) {
        Role newRole = roleService.addRole(role);
        return ResponseEntity.ok(newRole);
    }
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<RoleWithUserResponse> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        Optional<User> userOptional = userService.getUserByIdForRole(userId);
        Optional<Role> roleOptional = roleService.getRoleById(roleId);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();

            // Rolü kullanıcının rollerine ekle
            user.getRoles().add(role);

            // Kullanıcıyı güncelle
            userService.add(user); // Bu metodun implementasyonunu kontrol edin

            RoleWithUserResponse roleWithUserResponse = new RoleWithUserResponse(userId, roleId);
            return ResponseEntity.ok(roleWithUserResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
