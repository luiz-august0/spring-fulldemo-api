package com.springfulldemo.api.infrastructure.exceptions.enums;

public enum EnumGenericsException {
    GENERATE_TOKEN("Ocorreu um erro ao gerar o token de acesso"),
    GENERATE_REFRESH_TOKEN("Ocorreu um erro ao gerar o token de atualização"),
    VALIDATE_TOKEN("Ocorreu um erro ao validar o token"),
    EXPIRED_SESSION("Sessão expirada, realize o login novamente"),
    TOKEN_WITHOUT_SCHEMA("Não foi possível localizar schema no token"),
    INVALID_TENANT("Não foi possível localizar o tenant de acesso informado"),
    LOGIN_WITHOUT_TENANT("Não foi informado o tenant para a realização do login");
    private final String message;

    EnumGenericsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
