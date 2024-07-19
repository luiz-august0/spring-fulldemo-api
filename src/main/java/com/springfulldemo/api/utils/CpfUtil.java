package com.springfulldemo.api.utils;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;

public class CpfUtil {

    private static final Integer CPF_DIGITS = 11;

    public static boolean isValid(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return false;
        }

        value = StringUtil.keepOnlyNumbers(value);

        if (value.length() == CPF_DIGITS) {
            if ((value.compareTo("99999999999") == 0)
                    || (value.compareTo("00000000000") == 0)
                    || (value.compareTo("11111111111") == 0)
                    || (value.compareTo("22222222222") == 0)
                    || (value.compareTo("33333333333") == 0)
                    || (value.compareTo("44444444444") == 0)
                    || (value.compareTo("55555555555") == 0)
                    || (value.compareTo("66666666666") == 0)
                    || (value.compareTo("77777777777") == 0)
                    || (value.compareTo("88888888888") == 0)) {
                return false;
            }

            int DV1 = 0;
            int FirsNum = Integer.parseInt(value.substring(0, 1));
            int SecNum = Integer.parseInt(value.substring(1, 2));
            int ThrNum = Integer.parseInt(value.substring(2, 3));
            int FourNum = Integer.parseInt(value.substring(3, 4));
            int FivNum = Integer.parseInt(value.substring(4, 5));
            int SixNum = Integer.parseInt(value.substring(5, 6));
            int SeveNum = Integer.parseInt(value.substring(6, 7));
            int EighNum = Integer.parseInt(value.substring(7, 8));
            int NinNum = Integer.parseInt(value.substring(8, 9));
            int Sum = (FirsNum * 10) + (SecNum * 9) + (ThrNum * 8)
                    + (FourNum * 7) + (FivNum * 6) + (SixNum * 5)
                    + (SeveNum * 4) + (EighNum * 3) + (NinNum * 2);
            int Result = 11 - (Sum % 11);

            if ((Result == 10) || (Result == 11)) {
                DV1 = 0;
            } else {
                DV1 = Result;
            }

            int DV2;
            int FirsNum2 = Integer.parseInt(value.substring(0, 1));
            int SecNum2 = Integer.parseInt(value.substring(1, 2));
            int ThrNum2 = Integer.parseInt(value.substring(2, 3));
            int FourNum2 = Integer.parseInt(value.substring(3, 4));
            int FivNum2 = Integer.parseInt(value.substring(4, 5));
            int SixNum2 = Integer.parseInt(value.substring(5, 6));
            int SeveNum2 = Integer.parseInt(value.substring(6, 7));
            int EighNum2 = Integer.parseInt(value.substring(7, 8));
            int NinNum2 = Integer.parseInt(value.substring(8, 9));
            int Sum2 = (FirsNum2 * 11) + (SecNum2 * 10) + (ThrNum2 * 9)
                    + (FourNum2 * 8) + (FivNum2 * 7) + (SixNum2 * 6)
                    + (SeveNum2 * 5) + (EighNum2 * 4) + (NinNum2 * 3)
                    + (DV1 * 2);
            int Result2 = 11 - (Sum2 % 11);

            if ((Result2 == 10) || (Result2 == 11)) {
                DV2 = 0;
            } else {
                DV2 = Result2;
            }

            String VerDv = Integer.toString(DV1) + DV2;

            return VerDv.compareTo(value.substring(9, 11)) == 0;
        }

        return false;
    }


    public static boolean isCpf(String value) {
        if (StringUtil.isNotNullOrEmpty(value)) {
            value = StringUtil.keepOnlyNumbers(value);
            return value.length() == CPF_DIGITS;
        }

        return false;
    }

    public static void validate(String cpf) {
        if (!isValid(cpf)) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.CPF_INVALID);
        }
    }

    public static String format(String cpf) {
        if (cpf == null) {
            return cpf;
        }

        cpf = StringUtil.keepOnlyNumbers(cpf);

        return Utils.formatter(cpf, "###.###.###-##");
    }

}
