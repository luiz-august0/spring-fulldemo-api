package com.springfulldemo.api.utils;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;

public class CnpjUtil {

    private static final Integer CNPJ_DIGITS = 14;

    public static boolean isValid(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return false;
        }

        value = StringUtil.keepOnlyNumbers(value);
        
        if (value.length() == CNPJ_DIGITS) {
            if ((value.compareTo("99999999999999") == 0)
                    || (value.compareTo("00000000000000") == 0)
                    || (value.compareTo("11111111111111") == 0)
                    || (value.compareTo("22222222222222") == 0)
                    || (value.compareTo("33333333333333") == 0)
                    || (value.compareTo("44444444444444") == 0)
                    || (value.compareTo("55555555555555") == 0)
                    || (value.compareTo("66666666666666") == 0)
                    || (value.compareTo("77777777777777") == 0)
                    || (value.compareTo("88888888888888") == 0)) {
                return false;
            }
            
            int DV1 = 0;
            int FirstNum = Integer.parseInt(value.substring(0, 1));
            int SecNum = Integer.parseInt(value.substring(1, 2));
            int ThrNum = Integer.parseInt(value.substring(2, 3));
            int FourNum = Integer.parseInt(value.substring(3, 4));
            int FiftNum = Integer.parseInt(value.substring(4, 5));
            int SixNum = Integer.parseInt(value.substring(5, 6));
            int SevNum = Integer.parseInt(value.substring(6, 7));
            int EighNum = Integer.parseInt(value.substring(7, 8));
            int NinNum = Integer.parseInt(value.substring(8, 9));
            int TenNum = Integer.parseInt(value.substring(9, 10));
            int ElevNum = Integer.parseInt(value.substring(10, 11));
            int TwelNum = Integer.parseInt(value.substring(11, 12));
            int Sum = (FirstNum * 5) + (SecNum * 4) + (ThrNum * 3)
                    + (FourNum * 2) + (FiftNum * 9) + (SixNum * 8)
                    + (SevNum * 7) + (EighNum * 6) + (NinNum * 5)
                    + (TenNum * 4) + (ElevNum * 3) + (TwelNum * 2);
            int Result = 11 - (Sum % 11);

            if ((Result == 10) || (Result == 11)) {
                DV1 = 0;
            } else {
                DV1 = Result;
            }
            
            int DV2;
            int FirstNum2 = Integer.parseInt(value.substring(0, 1));
            int SecNum2 = Integer.parseInt(value.substring(1, 2));
            int ThrNum2 = Integer.parseInt(value.substring(2, 3));
            int FourNum2 = Integer.parseInt(value.substring(3, 4));
            int FiftNum2 = Integer.parseInt(value.substring(4, 5));
            int SixNum2 = Integer.parseInt(value.substring(5, 6));
            int SevNum2 = Integer.parseInt(value.substring(6, 7));
            int EighNum2 = Integer.parseInt(value.substring(7, 8));
            int NinNum2 = Integer.parseInt(value.substring(8, 9));
            int TenNum2 = Integer.parseInt(value.substring(9, 10));
            int ElevNum2 = Integer.parseInt(value.substring(10, 11));
            int TwelNum2 = Integer.parseInt(value.substring(11, 12));
            int Soma2 = (FirstNum2 * 6) + (SecNum2 * 5) + (ThrNum2 * 4)
                    + (FourNum2 * 3) + (FiftNum2 * 2) + (SixNum2 * 9)
                    + (SevNum2 * 8) + (EighNum2 * 7) + (NinNum2 * 6)
                    + (TenNum2 * 5) + (ElevNum2 * 4) + (TwelNum2 * 3)
                    + (DV1 * 2);
            int Result2 = 11 - (Soma2 % 11);

            if ((Result2 == 10) || (Result2 == 11)) {
                DV2 = 0;
            } else {
                DV2 = Result2;
            }
            
            String VerDv = Integer.toString(DV1) + DV2;

            return VerDv.compareTo(value.substring(12, 14)) == 0;
        }

        return false;
    }

    public static boolean isCnpj(String value) {
        if (StringUtil.isNotNullOrEmpty(value)) {
            value = StringUtil.keepOnlyNumbers(value);
            return value.length() == CNPJ_DIGITS;
        }

        return false;
    }

    public static void validate(String cnpj) {
        if (!isValid(cnpj)) {
            throw new ApplicationGenericsException(EnumUnauthorizedException.CNPJ_INVALID);
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
