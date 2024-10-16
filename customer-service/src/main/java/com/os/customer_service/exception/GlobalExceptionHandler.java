package com.os.customer_service.exception;

import com.os.customer_service.exception.type.WrongUsernameOrPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.turkcell.tcell.exception.exceptions.details.BusinessExceptionDetails;
import org.turkcell.tcell.exception.exceptions.type.BaseBusinessException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(BaseBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BusinessExceptionDetails> handleBusinessException(BaseBusinessException exception) {
        BusinessExceptionDetails businessExceptionDetails = new BusinessExceptionDetails();
        businessExceptionDetails.setTitle(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessExceptionDetails);
    }
    @ExceptionHandler(WrongUsernameOrPassword.class)
    public ResponseEntity<String> handleWrongUsernameOrPassword(WrongUsernameOrPassword ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }
}