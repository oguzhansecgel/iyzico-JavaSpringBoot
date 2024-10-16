package com.os.customer_service.exception.type;


import org.springframework.security.core.AuthenticationException;

public class WrongUsernameOrPassword extends AuthenticationException {

    public WrongUsernameOrPassword(String msg) {
        super(msg);
    }
}
