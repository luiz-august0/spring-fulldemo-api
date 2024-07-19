package com.springfulldemo.api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {


    public static BigDecimal add(BigDecimal value, BigDecimal amount) {
        return nvl(value).add(nvl(amount));
    }

    public static BigDecimal subtract(BigDecimal value, BigDecimal amount) {
        return nvl(value).subtract(nvl(amount));
    }

    public static BigDecimal multiply(BigDecimal value, BigDecimal amount) {
        return nvl(value).multiply(nvl(amount));
    }

    public static BigDecimal divide(BigDecimal value, BigDecimal amount) {
        return nvl(value).divide(nvl(amount), 4, RoundingMode.HALF_UP);
    }

    public static int compareTo(BigDecimal value, BigDecimal amount) {
        return nvl(value).compareTo(nvl(amount));
    }

    private static BigDecimal nvl(BigDecimal amount) {
        return Utils.nvl(amount, BigDecimal.ZERO);
    }

    public static BigDecimal valueOf(String value) {
        if (value != null) {
            Double doubleValue = Double.valueOf(value);
            return BigDecimal.valueOf(doubleValue);
        }
        return null;
    }

    public static BigDecimal truncBig(BigDecimal value, Integer decimais) {
        value = value.setScale(decimais, BigDecimal.ROUND_FLOOR);
        return value;
    }

}
