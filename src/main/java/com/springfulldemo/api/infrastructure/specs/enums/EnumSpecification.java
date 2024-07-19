package com.springfulldemo.api.infrastructure.specs.enums;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.specs.implementations.PredicateSpec;
import com.springfulldemo.api.infrastructure.specs.interfaces.IPredicateSpec;

import java.util.Arrays;

public enum EnumSpecification {
    EQUALS("EQUALS", "::", PredicateSpec.getPredicateSpecEquals()),
    NOTEQUALS("NOTEQUALS", ":<>:", PredicateSpec.getPredicateSpecNotEquals()),
    LIKE("LIKE", ":like:", PredicateSpec.getPredicateSpecLike()),
    GREATEROREQUALS("GREATEROREQUALS", ":>:", PredicateSpec.getPredicateSpecGreaterOrEquals()),
    LESSOREQUALS("LESSOREQUALS", ":<:", PredicateSpec.getPredicateSpecLessOrEquals());

    private String name;

    private String prefix;

    private com.springfulldemo.api.infrastructure.specs.interfaces.IPredicateSpec IPredicateSpec;

    EnumSpecification(String name, String prefix, IPredicateSpec IPredicateSpec) {
        this.name = name;
        this.prefix = prefix;
        this.IPredicateSpec = IPredicateSpec;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public IPredicateSpec getPredicateSpecInterface() {
        return IPredicateSpec;
    }

    public static EnumSpecification getEnumByPrefix(String prefixParam) {
        try {
            return Arrays.stream(EnumSpecification.values()).filter(
                    enumSpecification -> enumSpecification.getPrefix().equals(prefixParam)
            ).toList().getFirst();
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}
