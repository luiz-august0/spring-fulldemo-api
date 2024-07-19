package com.springfulldemo.api.infrastructure.exceptions.handlers;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.StandardError;
import com.springfulldemo.api.infrastructure.exceptions.ValidatorException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationGenericsException.class)
    public ResponseEntity<StandardError> generics(ApplicationGenericsException e, HttpServletRequest request) {
        String error = e.getClass().getName();
        HttpStatus status = e.getStatus();
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<StandardError> generics(ValidatorException e, HttpServletRequest request) {
        String error = e.getClass().getName();
        HttpStatus status = e.getStatus();
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> generics(AuthenticationException e, HttpServletRequest request) {
        String message = e.getClass().equals(InsufficientAuthenticationException.class)
                ? EnumUnauthorizedException.USER_ROLE_UNAUTHORIZED.getMessage() : e.getMessage();

        String error = e.getClass().getName();
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(new Date(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}