package com.springfulldemo.api.infrastructure.exceptions.enums;

public enum EnumUnauthorizedException {
    USER_ALREADY_REGISTERED("Usuário já cadastrado"),
    USER_INACTIVE("Usuário não está ativo"),
    WRONG_CREDENTIALS("Usuário ou senha incorretos"),
    CNPJ_INVALID("CNPJ inválido"),
    CNPJ_CPF_INVALID("CPF/CNPJ inválido"),
    CPF_INVALID("CPF inválido"),
    CPF_ALREADY_REGISTERED("CPF já cadastrado"),
    CNPJ_ALREADY_REGISTERED("CNPJ já cadastrado"),
    EMPTY_ITEMS_ORDER("Não há itens no pedido"),
    ORDER_ALREADY_CLOSED("Pedido já foi fechado"),
    ADMIN_CANNOT_BE_DEACTIVATED("Usuário admin não pode ser desativado"),
    USER_ROLE_UNAUTHORIZED("Vocẽ não tem acesso para realizar esta operação");

    private final String message;

    EnumUnauthorizedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
