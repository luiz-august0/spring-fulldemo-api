package com.springfulldemo.api.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

public class ValidatorException extends RuntimeException {
    private HttpStatus status;

    public ValidatorException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public static String mountMessageToRequiredField(String portugueseField) {
        return "O campo " + portugueseField + " é obrigatório";
    }

    public static String mountMessageToGreaterThanOrEqualZeroField(String portugueseField) {
        return "O campo " + portugueseField + " deve ser maior ou igual a 0";
    }

    public static String mountMessageToCharacterLengthField(String portugueseField, Integer value, Boolean max) {
        String message = "O valor do campo " + portugueseField;

        if (max) {
            message += " excede o máximo de " + value + " caracteres";
        } else {
            message += " não atende ao requisito mínimo de " + value + " caracteres";
        }

        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
