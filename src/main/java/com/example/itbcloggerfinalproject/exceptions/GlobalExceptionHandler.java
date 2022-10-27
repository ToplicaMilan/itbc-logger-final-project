package com.example.itbcloggerfinalproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameException.class)
    public ErrorMessage invalidUsernameException(InvalidUsernameException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorMessage invalidPasswordException(InvalidPasswordException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorMessage forbiddenException(ForbiddenException e) {
        return createErrorMessage(e.getMessage());
    }

    private ErrorMessage createErrorMessage(String message) {
        return ErrorMessage.builder().errorMessage(message).build();
    }
}