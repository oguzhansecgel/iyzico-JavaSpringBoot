package com.os.payment_service.exception.type;

public class ContactInfosNotFoundException extends RuntimeException {
    public ContactInfosNotFoundException(String message) {
        super(message);
    }
}
