package com.os.customer_service.exception.type;

import org.turkcell.tcell.exception.exceptions.type.BaseBusinessException;

public class PasswordNotMatchException extends BaseBusinessException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
