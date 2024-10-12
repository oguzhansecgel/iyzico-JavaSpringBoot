package com.os.customer_service.exception.type;

import org.turkcell.tcell.exception.exceptions.type.BaseBusinessException;

public class ContactInfoNotFoundException extends BaseBusinessException {
    public ContactInfoNotFoundException(String message) {
        super(message);
    }
}
