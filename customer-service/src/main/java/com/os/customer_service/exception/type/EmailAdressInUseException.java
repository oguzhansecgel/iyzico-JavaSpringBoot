package com.os.customer_service.exception.type;

public class EmailAdressInUseException extends RuntimeException{
    public EmailAdressInUseException(String message) {
        super(message);
    }
}
