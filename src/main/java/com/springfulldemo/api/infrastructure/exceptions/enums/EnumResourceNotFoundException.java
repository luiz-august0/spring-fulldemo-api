package com.springfulldemo.api.infrastructure.exceptions.enums;

public enum EnumResourceNotFoundException {
    RESOURCE_NOT_FOUND("Não foi possível encontrar registro");

    private final String message;

    EnumResourceNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
