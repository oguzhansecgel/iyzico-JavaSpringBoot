package com.os.customer_service.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdUserResponse {
    private int id;
    private String password;
    private String passwordRepeat;
    private String email;
    private String firstName;
    private String lastName;
    private int roleId;
}