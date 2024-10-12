package com.os.customer_service.dto.response.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleWithUserResponse {
    private Long userId;

    private Long roleId;
}