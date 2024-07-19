package com.springfulldemo.api.infrastructure.exceptions;

import com.springfulldemo.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumResourceInactiveException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumResourceNotFoundException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.springfulldemo.api.utils.StringUtil;
import org.springframework.http.HttpStatus;

public class ApplicationGenericsException extends RuntimeException {
    private HttpStatus status;

    public ApplicationGenericsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ApplicationGenericsException(EnumGenericsException exception) {
        super(exception.getMessage());
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ApplicationGenericsException(EnumUnauthorizedException exception) {
        super(exception.getMessage());
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public ApplicationGenericsException(EnumResourceNotFoundException exception) {
        super(exception.getMessage());
        this.status = HttpStatus.NOT_FOUND;
    }

    public ApplicationGenericsException(EnumResourceNotFoundException exception, String portugueseClassName, Integer id) {
        super(exception.getMessage() + " de " + portugueseClassName + " com id " + id);
        this.status = HttpStatus.NOT_FOUND;
    }

    public ApplicationGenericsException(EnumResourceInactiveException exception, String portugueseClassName, Integer id) {
        super(StringUtil.capitalizeAndShorthand(portugueseClassName) + " com id " + id + " " + exception.getMessage());
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public ApplicationGenericsException(EnumResourceInactiveException exception, String portugueseClassName, String objectName) {
        super(StringUtil.capitalizeAndShorthand(portugueseClassName) + " " + objectName + " " + exception.getMessage());
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
