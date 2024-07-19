package com.springfulldemo.api.model.enums;

public enum EnumUserRole {
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    private String role;

    EnumUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
