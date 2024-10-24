package com.os.payment_service.exception;

import com.os.payment_service.exception.error.ApiError;
import com.os.payment_service.exception.type.ContactInfosNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContactInfosNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFoundException(ContactInfosNotFoundException exception, HttpServletRequest request)
    {
        ApiError apiError  = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setPath(request.getRequestURI());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
