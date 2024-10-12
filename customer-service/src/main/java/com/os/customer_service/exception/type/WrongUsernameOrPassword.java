package com.os.customer_service.exception.type;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class WrongUsernameOrPassword extends InternalAuthenticationServiceException {
    public WrongUsernameOrPassword(String message, Throwable cause) {
        super(message, cause);
    }
}
