package com.springfulldemo.api.utils;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;

import java.util.function.Function;

public class CpfCnpjUtil {

    public static boolean isValid(String cpfCnpj) {
        if (CpfUtil.isCpf(cpfCnpj)) {
            return CpfUtil.isValid(cpfCnpj);
        } else if (CnpjUtil.isCnpj(cpfCnpj)) {
            return CnpjUtil.isValid(cpfCnpj);
        }
        return false;
    }

    public static void validate(String cpfCnpj) {
        boolean isCPF = validate(cpfCnpj, CpfUtil::isCpf, CpfUtil::isValid);
        boolean isCnpj = validate(cpfCnpj, CnpjUtil::isCnpj, CnpjUtil::isValid);
        boolean isCpfOrCnpj = isCPF || isCnpj;

        if (!isCpfOrCnpj) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.CNPJ_CPF_INVALID);
        }
    }

    private static boolean validate(String cpfCnpj, Function<String, Boolean> isDocFn, Function<String, Boolean> isValidFn) {
        boolean isDoc = isDocFn.apply(cpfCnpj);

        if (isDoc && !isValidFn.apply(cpfCnpj)) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.CNPJ_CPF_INVALID);
        }

        return isDoc;
    }

    public static String format(String cpfCnpj) {
        if (CpfUtil.isCpf(cpfCnpj)) {
            return CpfUtil.format(cpfCnpj);
        } else if (CnpjUtil.isCnpj(cpfCnpj)) {
            return CnpjUtil.format(cpfCnpj);
        }
        return cpfCnpj;
    }

}
